package io.github.mcrowley19.nn;

import java.util.ArrayList;
import java.lang.Math;
import io.github.mcrowley19.engine.Value;


public class Neuron  {
    ArrayList<Value> w = new ArrayList<Value>();
    Value b;
    boolean nonlin = true;

    public Neuron(int nin, boolean nonlin){
        for(int counter = 0; counter < nin; counter++){
            double randomNum = (Math.random() * 2) -1;
            this.w.add(new Value(randomNum));
        }
        this.b = new Value(0);
        this.nonlin = nonlin;
    }

    public Neuron(int nin){
        this(nin, false);
    }

    public ArrayList<Value> parameters(){
        ArrayList<Value> returnList = new ArrayList<Value>(w);
        returnList.add(b);
        return returnList;
    }

    public Value valueCall(ArrayList<Value> x){
        Value act = this.b;
        for(int index = 0; index < this.w.size(); index++ ){
            Value xb = this.w.get(index).mul(x.get(index));
            act = act.add(xb);
        }
        return this.nonlin ? act.tanh() : act;
    }

    public Value call(ArrayList<Double> x){
        ArrayList<Value> newList = new ArrayList<Value>();
        for(Double value: x){
            newList.add(new Value(value));
        }
        return valueCall(newList);
    }
    
    @Override
    public String toString(){
        return (this.nonlin ? "tanh": "Linear")+"Neuron("+this.w.size()+")";
    }

}
