/**
 * generated by Xtext 2.25.0
 */
package dk.sdu.mmmi.mdsd.generator;

import com.google.common.collect.Iterators;
import dk.sdu.mmmi.mdsd.math.Binding;
import dk.sdu.mmmi.mdsd.math.Div;
import dk.sdu.mmmi.mdsd.math.Expression;
import dk.sdu.mmmi.mdsd.math.External;
import dk.sdu.mmmi.mdsd.math.ExternalUse;
import dk.sdu.mmmi.mdsd.math.LetBinding;
import dk.sdu.mmmi.mdsd.math.MathExp;
import dk.sdu.mmmi.mdsd.math.MathNumber;
import dk.sdu.mmmi.mdsd.math.Minus;
import dk.sdu.mmmi.mdsd.math.Mult;
import dk.sdu.mmmi.mdsd.math.Parameter;
import dk.sdu.mmmi.mdsd.math.Plus;
import dk.sdu.mmmi.mdsd.math.VarBinding;
import dk.sdu.mmmi.mdsd.math.VariableUse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class MathGenerator extends AbstractGenerator {
  private static Map<String, String> variables;
  
  private static boolean withLet = false;
  
  private static int counter = 0;
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    MathGenerator.withLet = false;
    MathGenerator.counter = 0;
    HashMap<String, String> _hashMap = new HashMap<String, String>();
    MathGenerator.variables = _hashMap;
    final MathExp math = Iterators.<MathExp>filter(resource.getAllContents(), MathExp.class).next();
    String _name = math.getName();
    String _plus = ("math_expression/" + _name);
    String _plus_1 = (_plus + ".java");
    fsa.generateFile(_plus_1, this.compile(math));
  }
  
  public CharSequence compile(final MathExp math) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package math_expression;");
    _builder.newLine();
    _builder.append("public class ");
    String _name = math.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      EList<VarBinding> _variables = math.getVariables();
      for(final VarBinding variable : _variables) {
        _builder.append("\t");
        _builder.append("public int ");
        String _name_1 = variable.getName();
        _builder.append(_name_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      int _size = math.getExternals().size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append("\t");
        _builder.append("private External external;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("public ");
        String _name_2 = math.getName();
        _builder.append(_name_2, "\t\t");
        _builder.append("(External external) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("this.external = external;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      } else {
        _builder.append("\t");
        _builder.append("public ");
        String _name_3 = math.getName();
        _builder.append(_name_3, "\t");
        _builder.append("() {}");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("  ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void compute() {");
    _builder.newLine();
    _builder.append("  \t");
    _builder.newLine();
    {
      EList<VarBinding> _variables_1 = math.getVariables();
      for(final VarBinding variable_1 : _variables_1) {
        _builder.append("  \t");
        String _name_4 = variable_1.getName();
        _builder.append(_name_4, "  \t");
        _builder.append(" = ");
        String _computeExpression = MathGenerator.computeExpression(variable_1.getExpression());
        _builder.append(_computeExpression, "  \t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    {
      if (MathGenerator.withLet) {
        _builder.append("    ");
        _builder.append("interface LetBinding {");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("public int getResult();");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    {
      int _size_1 = math.getExternals().size();
      boolean _greaterThan_1 = (_size_1 > 0);
      if (_greaterThan_1) {
        _builder.append("public interface External {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        {
          EList<External> _externals = math.getExternals();
          for(final External external : _externals) {
            _builder.append("public int ");
            String _id = external.getId();
            _builder.append(_id, "\t\t");
            _builder.append("(");
            {
              EList<Parameter> _parameters = external.getParameters();
              boolean _hasElements = false;
              for(final Parameter param : _parameters) {
                if (!_hasElements) {
                  _hasElements = true;
                } else {
                  _builder.appendImmediate(", ", "\t\t");
                }
                String _param = param.getParam();
                _builder.append(_param, "\t\t");
                _builder.append(" param");
                _builder.append(MathGenerator.counter = (MathGenerator.counter + 1), "\t\t");
              }
            }
            _builder.append(");");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected static String _computeExpression(final VarBinding binding) {
    String _xblockexpression = null;
    {
      MathGenerator.variables.put(binding.getName(), MathGenerator.computeExpression(binding.getExpression()));
      _xblockexpression = binding.getName();
    }
    return _xblockexpression;
  }
  
  protected static String _computeExpression(final MathNumber exp) {
    return Integer.valueOf(exp.getValue()).toString();
  }
  
  protected static String _computeExpression(final Plus exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getLeft());
    String _plus = ("(" + _computeExpression);
    String _plus_1 = (_plus + "+");
    String _computeExpression_1 = MathGenerator.computeExpression(exp.getRight());
    String _plus_2 = (_plus_1 + _computeExpression_1);
    return (_plus_2 + ")");
  }
  
  protected static String _computeExpression(final Minus exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getLeft());
    String _plus = ("(" + _computeExpression);
    String _plus_1 = (_plus + "-");
    String _computeExpression_1 = MathGenerator.computeExpression(exp.getRight());
    String _plus_2 = (_plus_1 + _computeExpression_1);
    return (_plus_2 + ")");
  }
  
  protected static String _computeExpression(final Mult exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getLeft());
    String _plus = ("(" + _computeExpression);
    String _plus_1 = (_plus + "*");
    String _computeExpression_1 = MathGenerator.computeExpression(exp.getRight());
    String _plus_2 = (_plus_1 + _computeExpression_1);
    return (_plus_2 + ")");
  }
  
  protected static String _computeExpression(final Div exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getLeft());
    String _plus = ("(" + _computeExpression);
    String _plus_1 = (_plus + "/");
    String _computeExpression_1 = MathGenerator.computeExpression(exp.getRight());
    String _plus_2 = (_plus_1 + _computeExpression_1);
    return (_plus_2 + ")");
  }
  
  protected static String _computeExpression(final ExternalUse exp) {
    String _xblockexpression = null;
    {
      String params = "";
      EList<Expression> _parameters = exp.getParameters();
      for (final Expression param : _parameters) {
        int _length = params.length();
        boolean _lessEqualsThan = (_length <= 0);
        if (_lessEqualsThan) {
          String _computeExpression = MathGenerator.computeExpression(param);
          String _plus = (params + _computeExpression);
          params = _plus;
        } else {
          String _computeExpression_1 = MathGenerator.computeExpression(param);
          String _plus_1 = ((params + ",") + _computeExpression_1);
          params = _plus_1;
        }
      }
      String _id = exp.getId();
      String _plus_2 = ("external." + _id);
      String _plus_3 = (_plus_2 + "(");
      String _plus_4 = (_plus_3 + params);
      _xblockexpression = (_plus_4 + ")");
    }
    return _xblockexpression;
  }
  
  protected static String _computeExpression(final LetBinding exp) {
    String _xblockexpression = null;
    {
      MathGenerator.withLet = true;
      String _name = exp.getName();
      String _plus = ("new LetBinding() {\n\t\tpublic int getResult(){\n\t\t\t\t\tint let" + _name);
      String _plus_1 = (_plus + " = ");
      String _computeExpression = MathGenerator.computeExpression(exp.getBinding());
      String _plus_2 = (_plus_1 + _computeExpression);
      String _plus_3 = (_plus_2 + ";\n\t\t\t\t\tint ");
      String _name_1 = exp.getName();
      String _plus_4 = (_plus_3 + _name_1);
      String _plus_5 = (_plus_4 + " = let");
      String _name_2 = exp.getName();
      String _plus_6 = (_plus_5 + _name_2);
      String _plus_7 = (_plus_6 + ";\n\t\t\t\t\treturn ");
      String _computeExpression_1 = MathGenerator.computeExpression(exp.getBody());
      String _plus_8 = (_plus_7 + _computeExpression_1);
      _xblockexpression = (_plus_8 + ";\n\t\t\t\t}\n\t\t\t}.getResult()");
    }
    return _xblockexpression;
  }
  
  protected static String _computeExpression(final VariableUse exp) {
    String _computeBinding = MathGenerator.computeBinding(exp.getRef());
    String _plus = ("(" + _computeBinding);
    return (_plus + ")");
  }
  
  protected static String _computeBinding(final VarBinding binding) {
    String _xblockexpression = null;
    {
      boolean _containsKey = MathGenerator.variables.containsKey(binding.getName());
      boolean _not = (!_containsKey);
      if (_not) {
        MathGenerator.computeExpression(binding);
      }
      String _name = binding.getName();
      String _plus = ("(" + _name);
      _xblockexpression = (_plus + ")");
    }
    return _xblockexpression;
  }
  
  protected static String _computeBinding(final LetBinding binding) {
    return binding.getName();
  }
  
  public static String computeExpression(final EObject exp) {
    if (exp instanceof Div) {
      return _computeExpression((Div)exp);
    } else if (exp instanceof ExternalUse) {
      return _computeExpression((ExternalUse)exp);
    } else if (exp instanceof LetBinding) {
      return _computeExpression((LetBinding)exp);
    } else if (exp instanceof MathNumber) {
      return _computeExpression((MathNumber)exp);
    } else if (exp instanceof Minus) {
      return _computeExpression((Minus)exp);
    } else if (exp instanceof Mult) {
      return _computeExpression((Mult)exp);
    } else if (exp instanceof Plus) {
      return _computeExpression((Plus)exp);
    } else if (exp instanceof VarBinding) {
      return _computeExpression((VarBinding)exp);
    } else if (exp instanceof VariableUse) {
      return _computeExpression((VariableUse)exp);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(exp).toString());
    }
  }
  
  public static String computeBinding(final Binding binding) {
    if (binding instanceof LetBinding) {
      return _computeBinding((LetBinding)binding);
    } else if (binding instanceof VarBinding) {
      return _computeBinding((VarBinding)binding);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(binding).toString());
    }
  }
}
