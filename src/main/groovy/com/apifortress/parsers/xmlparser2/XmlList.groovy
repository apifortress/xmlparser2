package com.apifortress.parsers.xmlparser2

import groovy.util.slurpersupport.GPathResult

/**
 *
 */
class XmlList extends LinkedList<XmlNode> implements IXmlItem {

    public static XmlList convert(GPathResult result){
        XmlList list = new XmlList()
        result.each {
            list.add(new XmlNode(it))
        }
        return list;
    }

    public XmlList(List<XmlNode> item){
        super()
        addAll(item)
    }

    private XmlList(){
        super()
    }

    public List<String> names(){
        return collect {it.name() }.unique()
    }

    public IXmlItem get(String name){
        def items = findAll { it.__name == name }
        if(items.isEmpty())
            return null
        if(items.size()==1)
            return items[0]
        return new XmlList(items)
    }
}
