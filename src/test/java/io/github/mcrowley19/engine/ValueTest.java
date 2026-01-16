package io.github.mcrowley19.engine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ValueTest {
    @Test
    public void test() {
        Value x = new Value(-4.0);

        Value z = x.mul(2).add(2).add(x);
        Value q = z.tanh().add(z.mul(x));

        Value h = (z.mul(z)).tanh();
        Value y = h.add(q).add(q.mul(x));

        y.backward();

        Value xmg = x;
        Value ymg = y;
 

        /*
        Ran in python

        x = torch.Tensor([-4.0]).double()
        x.requires_grad = True
        z = 2 * x + 2 + x
        q = z.tanh() + z * x
        h = (z * z).tanh()
        y = h + q + q * x
        y.backward()
        xpt, ypt = x.data.item(), y.data.item()
        
        */

        Double xpt = -4.0;
        Double ypt = -116.00000001236691;
        assertEquals(xmg.data, xpt);
        assertEquals(ymg.data,ypt);

    }
   
}
