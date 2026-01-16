package io.github.mcrowley19.engine;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;


public class Value {
    public double data;
    public HashSet<Value> _prev = new HashSet<Value>();
    public String _op = "";
    public double grad = 0;
    public Runnable _backward = () -> {};

    public Value(double data, ArrayList<Value> _children, String _op){
        this.data = data;
        this._prev =new HashSet<>(_children);
        this._op = _op;
    }
    public Value(double data, ArrayList<Value> _children){
        this(data, _children, "");
    }
    public Value(double data){
        this(data, new ArrayList<Value>(), "");
    }
    public Value(double data, String _op){
        this(data, new ArrayList<Value>(),"");
    }
    
    public Value add(Value other ){
        ArrayList<Value> _children = new ArrayList<>(Arrays.asList(this, other));
        Value out = new Value(this.data + other.data, _children, "+");
        out._backward = () -> {
            this.grad += 1.0 * out.grad;
            other.grad += 1.0 * out.grad;
        };
        return out;
    }

    public Value add(double other){
        Value newOther = new Value(other);
        return this.add(newOther);

    }

       public Value mul(Value other){
        ArrayList<Value> _children = new ArrayList<>(Arrays.asList(this, other));
        Value out = new Value(this.data * other.data, _children, "*");

        out._backward = () -> {
            this.grad += other.data * out.grad;
            other.grad += this.data * out.grad;
        };
        return out;
    }

    public Value mul(double other){
        Value newOther = new Value(other);
        return this.mul(newOther);

    }

    public Value pow(double other){
        ArrayList<Value> _children = new ArrayList<>(Arrays.asList(this));
        Value out = new Value(Math.pow(this.data,other), _children, "**"+other);

        out._backward = () -> {
            this.grad += (other * Math.pow(this.data, other -1)) * out.grad;
        };

        return out;
    }


    public Value tanh(){
        double x = this.data;
        double t = (Math.exp(2*x) -1) /(Math.exp(2*x) + 1);
        ArrayList<Value> _children = new ArrayList<>(Arrays.asList(this));
        Value out = new Value(t, _children, "tanh");

        out._backward = () -> {
            this.grad += (1 - Math.pow(t, 2)) * out.grad;
        };
        return out;
    }
     public void build_topo(Value v, HashSet<Value> visited, ArrayList<Value> topo ){
        if(!visited.contains(v)){
            visited.add(v);
            for(Value child: v._prev){
                build_topo(child, visited, topo);
            }
            topo.add(v);
        }
    }
 
     public void backward(){
        HashSet<Value> visited = new HashSet<Value> ();
        ArrayList<Value> topo = new ArrayList<Value>();

        build_topo(this, visited, topo);
        this.grad = 1;
        for(int valueIndex = topo.size() -1; valueIndex >= 0; valueIndex--){
            topo.get(valueIndex)._backward.run();
        }
    }

    public Value neg(){
        return this.mul(-1);
    }
    public Value sub(Value other ){
        return this.add(other.neg());
    }
    public Value sub(double other){
        Value newOther = new Value(other);
        return this.add(newOther.neg());

    }

    public Value div(Value other){
        return this.mul(other.pow(-1));
    }
    public Value div(double other){
        Value newOther = new Value(other);
        return this.div(newOther);
    }
    public Value exp(){
        double x = this.data;
        ArrayList<Value> _children = new ArrayList<>(Arrays.asList(this));
        Value out = new Value(Math.exp(x), _children, "exp");

        out._backward = () -> {
            this.grad += out.data * out.grad;
        };
        return out;

    }

    @Override
    public String toString(){
        return "Value(data = " + this.data + ")";
    }
}
