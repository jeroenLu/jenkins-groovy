#!/usr/bin/env groovy
RetValueClass retValueClass = new RetValueClass()

def call(String name = 'human') {
    echo "start api pipeline"

    echo retValueClass.getValue();



    echo "end api pipeline"
}