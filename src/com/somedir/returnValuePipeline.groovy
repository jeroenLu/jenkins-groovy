#!/usr/bin/env groovy
package com.somedir

class RetValueClass {

    def getValue() {
        echo "in return pipeline"
        return '100'
    }
}


