# micrograd Java Edition

![puppy](puppy.png)

This is a Java remake of Andrej Karpathy's micrograd Autograd engine.

### Installation through Maven

Clone the repo

```bash
git clone https://github.com/mcrowley19/micrograd-java
cd micrograd-java
mvn clean install
```

Include this in the pom.xml file of your project.

```xml
<dependency>
  <groupId>io.github.mcrowley19</groupId>
  <artifactId>micrograd-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Changes

Due to the fact this is written in Java, certain changes have been made to the operation of the library. As Java does not support operator overloading, division, multiplication, addition and exponentiation must be called using the built-in functions in the Value class. As a result, the operations also do not follow the usual operator precedence in Java.

Java also does not have an implementation of the **call** class method from Python so calling the Neuron, MLP or Layer classes must be done through the .call() method for lists of Doubles or the .callValue() method for lists of Value objects.

This library also does not include the draw_dot method from the original library and replaces the ReLU method with a tanh method.

### Example Usage

Below is a slightly contrived example showing a number of possible supported operations:

```java
import io.github.mcrowley19.engine.Value;

Value a = new Value(-4.0);
Value b = new Value(2.0);
Value c = a.add(b);
Value d = a.mul(b).add(b.pow(3));
c = c.add(1);
c = c.add(c.add(1).add(a.neg()));
d = d.add(d.mul(2).add(b.add(a).tanh()));
d = d.add(d.mul(3).add(b.sub(a)).tanh());
Value e = c.sub(d);
Value f = e.pow(2);
Value g = f.div(2.0);
g = g.add(10.0.div(f));

System.out.printf("%.4f",g.data); // prints 5.5397, the outcome of this forward pass
g.backward();
System.out.printf("%.4f",a.grad); // prints -11.4728, i.e. the numerical value of dg/da
System.out.printf("%.4f",b.grad); // prints -49.9067, i.e. the numerical value of dg/db
```

### License

MIT
