package com.bottle.util

import org.w3c.dom.Document
import org.xml.sax.InputSource
import org.xml.sax.SAXException

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.PrintWriter
import java.io.StringReader

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 *
 * @ClassName: XmlHelper
 * @Description: Xml Document帮助类
 * @author halfbottle
 * @date 2017.09.13
 */

/**
 * 将一个XML字符串转化为一个Document对象
 * @param xmlString xml字符串
 * @return Document
 * @throws ParserConfigurationException SAXException IOException
 */
fun xmlDomFromString(xmlString: String): Document? {
    var document: Document?
    try {
        val builderFactory = DocumentBuilderFactory.newInstance()
        val builder = builderFactory.newDocumentBuilder()
        val inputSource = InputSource(StringReader(xmlString))
        document = builder.parse(inputSource)
    } catch (e: ParserConfigurationException) {
        e.printStackTrace()
        throw e
    } catch (e: SAXException) {
        e.printStackTrace()
        throw e
    } catch (e: IOException) {
        e.printStackTrace()
        throw e
    }

    return document
}

/**
 * 将一个xml文件转化为Document对象
 * @param file
 * @return Document
 * @throws ParserConfigurationException SAXException IOException
 */
fun xmlDomFromFile(file: File): Document? {
    var document: Document?
    try {
        val builderFactory = DocumentBuilderFactory.newInstance()
        val builder = builderFactory.newDocumentBuilder()
        document = builder.parse(file)
    } catch (e: ParserConfigurationException) {
        e.printStackTrace()
        throw e
    } catch (e: SAXException) {
        e.printStackTrace()
        throw e
    } catch (e: IOException) {
        e.printStackTrace()
        throw e
    }

    return document
}

/**
 * 将一个InputStream转化为Document对象
 * @param inputStream
 * @return Document
 * @throws ParserConfigurationException SAXException IOException
 */
fun xmlDomFromStream(inputStream: InputStream): Document? {
    var document: Document?
    try {
        val builderFactory = DocumentBuilderFactory.newInstance()
        val builder = builderFactory.newDocumentBuilder()
        document = builder.parse(inputStream)
    } catch (e: ParserConfigurationException) {
        e.printStackTrace()
        throw e
    } catch (e: SAXException) {
        e.printStackTrace()
        throw e
    } catch (e: IOException) {
        e.printStackTrace()
        throw e
    }

    return document
}

/**
 * 将一个Document对象写入到xml文件
 * @param doc
 * @param filePath
 * @throws Exception
 */
fun domToXmlFile(doc: Document, filePath: String) {
    var pw: PrintWriter? = null
    try {
        val tf = TransformerFactory.newInstance()
        val transformer: Transformer
        transformer = tf.newTransformer()
        val source = DOMSource(doc)
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        pw = PrintWriter(File(filePath))
        val streamResult = StreamResult(pw)
        transformer.transform(source, streamResult)
    } catch (e: TransformerConfigurationException) {
        e.printStackTrace()
        throw e
    } catch (e: TransformerException) {
        e.printStackTrace()
        throw e
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        throw e
    } finally {
        pw!!.close()
    }
}
