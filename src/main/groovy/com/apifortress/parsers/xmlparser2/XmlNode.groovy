package com.apifortress.parsers.xmlparser2

import groovy.util.slurpersupport.GPathResult
import groovy.xml.XmlUtil

/**
 *
 */
class XmlNode implements IXmlItem,Iterable<XmlNode> {

    private final XmlList __children

    private final Map<String,String> __attributes

    private final String __name

    private String __text

    public XmlNode(GPathResult item){
        __attributes = new HashMap<String,String>()

        __name = item.name()

        item.attributes().each {
            if(!it.key.startsWith('{'))
                setAttribute(it.key,it.value)
        }

        __children = XmlList.convert(item.children())

        __text = mergeTextNodes(item.localText())

    }

    public String getAttribute(String key){
        return __attributes.get(key)
    }

    public void setAttribute(String key,String value){
        __attributes.put(key,value)
    }

    public int size(){
        return __children.size()
    }

    public def get(String name){
        if(name.startsWith('@'))
            return getAttribute(name.drop(1).toString())
        if(name=='*')
            return __children
        return __children.get(name)
    }

    public def getAt(int pos){
        if(size()==0)
            return this
        return __children.getAt(pos)
    }

    public def pick(int quantity){
        if(size()==0)
            return [this]
        retufn __children.pick(quantity)
    }

    public Map<String,String> attributes(){
        return __attributes
    }

    public String name(){
        return __name
    }
    public String text(){
        return __text
    }

    public void setText(String text){
        this.__text = text
    }

    public boolean add(XmlNode node){
        return __children.add(node)
    }

    public boolean insert(int index,XmlNode node){
        return __children.add(index,node)
    }

    @Override
    Iterator<XmlNode> iterator() {
        return [this].iterator()
    }

    public XmlList children(){
        return __children
    }

    private static String mergeTextNodes(List nodes){
        StringBuilder sb = new StringBuilder()
        nodes.each {
            sb.append(it)
        }
        return sb.toString()
    }

    public String toString(){
        StringBuilder sb = new StringBuilder()
        sb.append('<'+__name)
        composeAttributes(sb)
        sb.append('>')
        if(__children){

            __children.each {
                sb.append(it.toString())
            }
        }
        sb.append(XmlUtil.escapeXml(__text))
        sb.append('</'+__name+'>')
        return sb.toString()
    }

    public def names(){
        return children().names()
    }

    public def groupedNodes(){
        return children().groupBy {it.name()}.values()
    }

    private void composeAttributes(StringBuilder sb){
        __attributes.each {
            sb.append(' '+it.key+'="'+XmlUtil.escapeXml(it.value)+'"')
        }
    }

    public def getProperties(){
        if(children().names().contains('properties'))
            return get('properties')
        return super.getProperties()
    }

    public String asXml(){
        return toString()
    }
    public String asXML(){
        return toString()
    }
}
