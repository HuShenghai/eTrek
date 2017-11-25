package com.bottle.library.midi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class MidiFile {
    // Note lengths
    //  We are working with 32 ticks to the crotchet. So
    //  all the other note lengths can be derived from this
    //  basic figure. Note that the longest note we can
    //  represent with this code is one tick short of a
    //  two semibreves (i.e., 8 crotchets)


    // Standard MIDI file header, for one-track file
    // 4D, 54... are just magic numbers to identify the headers
    // Note that because we're only writing one track, we
    // can for simplicity combine the file and track headers
    private int header[] = new int[]{
            0x4d, 0x54, 0x68, 0x64, 0x00, 0x00, 0x00, 0x06,
            0x00, 0x01, // multi-track format synchronous
            0x00, 0x10, // two track
            0x00, 0x78, // 16 ticks per quarter
    };

    private int channelHeader[] = new int[]{
            0x4d, 0x54, 0x72, 0x6B
    };

    // Standard footer
    private int channelFooter[] = new int[]{
            0x01, 0xFF, 0x2F, 0x00
    };

    // A MIDI event to set the tempo(速度)
    private int tempoEvent[] = new int[]{
            0x00, 0xFF, 0x51, 0x03, 0x0F, 0x42, 0x40 // Default 1 million usec per crotchet
    };

    // A MIDI event to set the key signature. This is irrelent to
    //  playback, but necessary for editing applications(升降调)
    private int keySigEvent[] = new int[] {
        0x00, 0xFF, 0x59, 0x02,
                0x00, // C
                0x00  // major
    }

    ;

    // A MIDI event to set the time signature. This is irrelent to
    //  playback, but necessary for editing applications(节拍)
    private int timeSigEvent[] = new int[] {
        0x00, 0xFF, 0x58, 0x04,
                0x04, // numerator
                0x02, // denominator (2==4, because it's a power of 2)
                0x30, // ticks per click (not used)
                0x08  // 32nd notes per crotchet
    }

    ;

    private Vector<MidiChannel> mChannels = new Vector<MidiChannel>();

    public MidiFile() {
    }

    /**
     * Construct a new MidiFile with an empty playback event list
     */
    public MidiFile(float tempo/*secPerCrotchet,default 1.0f*/, int keySig/*default 0, must between -7 to 7*/,
                    int timeSigNumerator/*default 4*/, int timeSigDenominator/*default 2, exponent of 2*/) {
        if (Math.abs(tempo - 1.0f) > 0.001f) {
            int microSec = (int) (tempo * 1000000);
            tempoEvent[6] = microSec % 0x100;
            tempoEvent[5] = ((int) (microSec / 0x100)) % 0x100;
            tempoEvent[4] = microSec / 0x10000;
        }

        keySigEvent[4] = keySig;

        timeSigEvent[4] = timeSigNumerator;
        timeSigEvent[5] = timeSigDenominator;
    }

    public void setBaseTicks(int ticks) {
        byte[] bytes = intToByteArray(ticks);
        header[12] = bytes[2];
        header[13] = bytes[3];
    }

    public int getBaseTicks() {
        return (header[12] * 0x100 + header[13]);
    }

    public void setTempo(float tempo) {
        int microSec = (int) (tempo * 1000000);
        tempoEvent[6] = microSec % 0x100;
        tempoEvent[5] = (microSec / 0x100) % 0x100;
        tempoEvent[4] = microSec / 0x10000;
    }

    public float getTempo() {
        return (tempoEvent[4] * 0x10000 + tempoEvent[5] * 0x100 + tempoEvent[6]) / 1000000.0f;
    }

    public void setKeySig(int keySig) {
        keySigEvent[4] = keySig;
    }

    public int getKeySig() {
        return keySigEvent[4];
    }

    public void setTimeSig(int timeSigNumerator, int timeSigDenominator) {
        timeSigEvent[4] = timeSigNumerator;
        timeSigEvent[5] = timeSigDenominator;
    }

    public int getTimeSig() {
        return timeSigEvent[4];
    }

    public void addChannel(MidiChannel channel) {
        mChannels.add(channel);
    }

    /**
     * Write the stored MIDI events to a file
     */
    public void writeToFile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);

        header[11] = mChannels.size() + 1;

        fos.write(intArrayToByteArray(header));

        fos.write(intArrayToByteArray(channelHeader));

        // Calculate the amount of track data
        // _Do_ include the footer but _do not_ include the track header

        int size = tempoEvent.length + keySigEvent.length + timeSigEvent.length + channelFooter.length;

        fos.write(intToByteArray(size));

        // Write the standard metadata — tempo, etc
        // At present, tempo is stuck at crotchet=60
        fos.write(intArrayToByteArray(tempoEvent));
        fos.write(intArrayToByteArray(keySigEvent));
        fos.write(intArrayToByteArray(timeSigEvent));

        //end global track
        fos.write(intArrayToByteArray(channelFooter));

        for (int i = 0; i < mChannels.size(); ++i) {
            fos.write(intArrayToByteArray(channelHeader));
            size = channelFooter.length;
            MidiChannel channel = mChannels.elementAt(i);
            for (int j = 0; j < channel.playEvents.size(); ++j)
                size += channel.playEvents.elementAt(j).length;

            fos.write(intToByteArray(size));

            // Write out the note, etc., events
            for (int j = 0; j < channel.playEvents.size(); ++j) {
                fos.write(intArrayToByteArray(channel.playEvents.elementAt(j)));
            }
            // Write the footer and close
            fos.write(intArrayToByteArray(channelFooter));
        }

        fos.close();
    }

    public void release() {
        mChannels.clear();
    }

    /**
     * Convert an array of integers which are assumed to contain
     * unsigned bytes into an array of bytes
     */
    protected static byte[] intArrayToByteArray(int[] ints) {
        int l = ints.length;
        byte[] out = new byte[ints.length];
        for (int i = 0; i < l; i++) {
            out[i] = (byte) ints[i];
        }
        return out;
    }

    protected static byte[] intToByteArray(int i) {
        // Write out the track data size in big-endian format
        // Note that this math is only valid for up to 64k of data
        //  (but that's a lot of notes)
        byte[] out = new byte[4];
        out[0] = 0;
        out[1] = 0;
        out[2] = (byte) (i / 256);
        out[3] = (byte) (i - ((int) out[2] * 256));
        return out;
    }
}