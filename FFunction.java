

import edu.hws.jcm.data.Expression;
import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.Variable;

public class FFunction implements Evaluatable {
    private Parser parser;
    private Variable parameter;
    private Variable variable;
    private Expression fun;
    private Expression der;

    public FFunction(String fun_str) {
        parser = new Parser(Parser.STANDARD_FUNCTIONS);

        variable = new Variable("x", 0);
        parameter = new Variable("a", 1);

        parser.add(variable);
        parser.add(parameter);

        fun = parser.parse(fun_str);
        der = fun.derivative(variable);
    }

    public FFunction(String fun_str, double a) {
        parser = new Parser(Parser.STANDARD_FUNCTIONS);

        variable = new Variable("x");
        parameter = new Variable("a",a);
        
        parser.add(variable);
        parser.add(parameter);

        fun = parser.parse(fun_str);
        der = fun.derivative(variable);
    }

    public double get_a() { return parameter.getVal(); }

    public void set_a(double a) { this.parameter.setVal(a); }

    public String get_fun() { return fun.toString(); }

    public String  get_der() { return der.toString(); }

    public void set_fun(String fun_str){
        if (fun_str.equals(fun.toString())) return;

        fun = parser.parse(fun_str);
        der = fun.derivative(variable);
    }

    public double derivative(double x){
        variable.setVal(x);
        return der.getVal();
    }


    @Override
    public double evalf(double x) {
        variable.setVal(x);
        return fun.getVal();
    }
}