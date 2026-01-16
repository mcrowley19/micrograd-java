package io.github.mcrowley19.nn;

import java.util.ArrayList;
import io.github.mcrowley19.engine.Value;



public class MLP {
    ArrayList<Layer> layers = new ArrayList<Layer>();
    public MLP(int nin, ArrayList<Integer> nouts){

        ArrayList<Integer> sz = new ArrayList<Integer>();
        sz.add(nin);
        sz.addAll(nouts);
        for(int i = 0; i < nouts.size(); i++){
            this.layers.add(new Layer(sz.get(i), sz.get(i+1), i!= nouts.size()-1));
        }
    }

    public ArrayList<Value> parameters(){
        ArrayList<Value> params = new ArrayList<>(); 
        for(Layer layer: this.layers){
            params.addAll(layer.parameters());
        }
        return params;
    }
    public ArrayList<Value> valueCall(ArrayList<Value> x){
        for(Layer layer: this.layers){
            x = layer.valueCall(x);
        }
        return x;
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
        ArrayList<String> layerStrings = new ArrayList<String>();
        for(Layer layer: this.layers){
            layerStrings.add(layer.toString());
        }
        return "MLP of "+layerStrings;
    }
}
