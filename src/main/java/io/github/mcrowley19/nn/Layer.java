package io.github.mcrowley19.nn;

import java.util.ArrayList;
import io.github.mcrowley19.engine.Value;


public class Layer {
    ArrayList<Neuron> neurons = new ArrayList<Neuron>();
    public Layer(int nin, int nout, boolean nonLin){
        for(int output = 0; output< nout; output++ ){
            this.neurons.add(new Neuron(nin, nonLin));
        }
    }
    public Layer(int nin, int nout){
        this(nin, nout, true);
    }
    public ArrayList<Value> parameters(){
        ArrayList<Value> params = new ArrayList<Value>();
        for(Neuron neuron: this.neurons){
            params.addAll(neuron.parameters());
        }
        return params;
    }
    public ArrayList<Value> valueCall(ArrayList<Value> x){
        ArrayList<Value> outs = new ArrayList<Value>();
        for(Neuron neuron: this.neurons){
            outs.add(neuron.valueCall(x));
        }
        
        return outs;
    }
    public ArrayList<Value> call(ArrayList<Double> x){
        ArrayList<Value> newList = new ArrayList<Value>();
        for(Double value: x){
            newList.add(new Value(value));
        }
        return valueCall(newList);
    }

     @Override
    public String toString(){
        ArrayList<String> neuronStrings = new ArrayList<String>();
        for(Neuron neuron: this.neurons){
            neuronStrings.add(neuron.toString());
        }
        return "Layer of "+neuronStrings;
    }


    
}
