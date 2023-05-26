package com.example.studyspringgradle.global;

import lombok.Getter;

@Getter
public class Tuple<ObjectA, ObjectB> {
    private final ObjectA objectA;
    private final ObjectB objectB;

    public Tuple(Tuple<ObjectA, ObjectB> tuple){
        this.objectA = tuple.getObjectA();
        this.objectB = tuple.getObjectB();
    }

    public Tuple(ObjectA objectA, ObjectB objectB){
        this.objectA = objectA;
        this.objectB = objectB;
    }

    public static <ObjectA, ObjectB> Tuple<ObjectA, ObjectB> of(ObjectA objectA, ObjectB objectB){
        return new Tuple<ObjectA, ObjectB>(objectA, objectB);
    }
}
