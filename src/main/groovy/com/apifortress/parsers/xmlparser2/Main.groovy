package com.apifortress.parsers.xmlparser2

/**
 *
 */
class Main {

    public static void main(def args){
        XmlNode node = new XmlNode(new XmlSlurper().parse(new File('stuff.xml')))
        println node['__name']
    }
}
