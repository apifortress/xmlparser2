package com.apifortress.parsers.xmlparser2

/**
 *
 */
class Main {

    public static void main(def args){
        XmlNode node = new XmlNode(new XmlSlurper().parse(new File('stuff.xml')))
        node.gino.each {
            println it.name()
        }
        node.gino.foobar.each {
            println it['@attr']
        }
        node.gino.foobar[0].each {
            println it['@attr']
        }
        node.gino['*'].each {
            println it.name()
        }

    }
}
