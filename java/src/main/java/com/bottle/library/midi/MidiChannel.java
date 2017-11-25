package com.bottle.library.midi;

import java.util.Vector;

public class MidiChannel {
    private int mChannelIndex = 1;

    // The collection of events to play, in time order
    public Vector<int[]> playEvents = new Vector<int[]>();

    public MidiChannel() {
    }

    public MidiChannel(int channelIndex/*default 1, between 00 to FF*/) {
        mChannelIndex = channelIndex;
    }

    public void setChannelIndex(int channelIndex) {
        mChannelIndex = channelIndex;
    }

    /**
     * Store a program-change event at current position
     */
    public void progChange(int prog/*0x00-0x7f*/) {
        int[] data = new int[3];
        data[0] = 0;
        data[1] = 0xC0 + mChannelIndex;
        data[2] = prog;
        playEvents.add(data);
    }

    /**
     * Store a note-on event
     */
    public void noteOn(int delta, int note, int velocity) {
        int[] data = new int[4];
        data[0] = delta;
        data[1] = 0x90 + mChannelIndex;
        data[2] = note;
        data[3] = velocity;
        playEvents.add(data);
    }

    /**
     * Store a note-off event
     */
    public void noteOff(int delta, int note) {
        int[] data = new int[4];
        data[0] = delta;
        data[1] = 0x80 + mChannelIndex;
        data[2] = note;
        data[3] = 0;
        playEvents.add(data);
    }

    /**
     * Store a note-on event followed by a note-off event a note length
     * later. There is no delta value — the note is assumed to
     * follow the previous one with no gap.
     */
    public void noteOnOffNow(int duration, int note, int velocity) {
        noteOn(0, note, velocity);
        noteOff(duration, note);
    }

    public void noteSequenceFixedVelocity(int[] sequence, int velocity) {
        boolean lastWasRest = false;
        int restDelta = 0;
        for (int i = 0; i < sequence.length; i += 2) {
            int note = sequence[i];
            int duration = sequence[i + 1];
            if (note < 0) {
                // This is a rest
                restDelta += duration;
                lastWasRest = true;
            } else {
                // A note, not a rest
                if (lastWasRest) {
                    noteOn(restDelta, note, velocity);
                    noteOff(duration, note);
                } else {
                    noteOn(0, note, velocity);
                    noteOff(duration, note);
                }
                restDelta = 0;
                lastWasRest = false;
            }
        }
    }
}
