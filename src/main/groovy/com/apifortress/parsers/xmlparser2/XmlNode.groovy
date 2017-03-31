package com.apifortress.parsers.xmlparser2

import groovy.util.slurpersupport.GPathResult

/**
 *
 */
class XmlNode implements IXmlItem,Iterable<XmlNode> {

    private final XmlList children

    private final Map<String,String> attributes

    private final String name

    private String text

    public XmlNode(GPathResult item){
        attributes = new HashMap<String,String>()

        name = item.name()

        item.attributes().each {
            if(!it.key.startsWith('{'))
                setAttribute(it.key,it.value)
        }

        children = XmlList.convert(item.children())

        text = mergeTextNodes(item.localText())

    }

    public String getAttribute(String key){
        return attributes.get(key)
    }

    public void setAttribute(String key,String value){
        attributes.put(key,value)
    }

    public int size(){
        return children.size()
    }

    public String getName(){
        return name;
    }

    public def get(String name){
        if(name.startsWith('@'))
            return getAttribute(name.drop(1).toString())
        if(name=='*')
            return children
        return children.get(name)
    }

    public def getAt(int pos){
        if(size()==0)
            return this
        return children.getAt(pos)
    }

    public Map<String,String> attributes(){
        return attributes
    }

    public String name(){
        return name
    }
    public String text(){
        return text
    }

    public void setText(String text){
        this.text = text
    }

    public boolean add(XmlNode node){
        return children.add(node)
    }

    public boolean insert(int index,XmlNode node){
        return children.add(index,node)
    }

    @Override
    Iterator<XmlNode> iterator() {
        return [this].iterator()
    }

    public XmlList children(){
        return children
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
        sb.append('<'+name+' ')
        composeAttributes(sb)
        if(text)
          return text
         else
        if(children){
            sb.append('>\n')
            children.each {
                sb.append(it.toString())
            }
            sb.append('</'+name+'>\n')
        }
        else
            sb.append('/>\n')
        return sb.toString()

    }

    public def names(){
        return children().collect{ it.name() }
    }

    public def groupedNodes(){
        return children().groupBy {it.name()}.values()
    }

    private void composeAttributes(StringBuilder sb){
        attributes.each {
            sb.append(it.key+'="'+it.value+'" ')
        }
    }
}
