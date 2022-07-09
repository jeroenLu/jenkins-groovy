#!/usr/bin/env groovy

def call(String name = 'human') {
    echo "start api pipeline"

    echo new RetValueClass().getValue();



    echo "end api pipeline"
}