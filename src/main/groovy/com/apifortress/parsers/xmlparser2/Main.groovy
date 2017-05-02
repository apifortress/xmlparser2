package com.apifortress.parsers.xmlparser2

/**
 *
 */
class Main {

    public static void main(def args){
        XmlNode node = new XmlNode(new XmlSlurper().parse(new File('stuff2.xml')))
        node.Artist.pick(5).each{
            println it['@href']
        }
    }
}
