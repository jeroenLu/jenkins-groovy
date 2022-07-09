#!/usr/bin/env groovy
import com.somedir.RetValueClass

def call(String name = 'human') {
    echo "start api pipeline"

    def valueClass = new RetValueClass();
    echo valueClass.getValue();



    echo "end api pipeline"
}