package com.example.liusk.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, b_ac, b_de, b_add, b_sub, b_mul, b_div, b_per,  b_p,b_equ;
    private TextView text;
    private EditText edit;
    private ArrayList<Character> opt = new ArrayList<>();

    @Override
    //创建界面并且给按钮设置监听器
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //按照id获取按钮
        b1 = findViewById(R.id.c_one);
        b2 = findViewById(R.id.c_two);
        b3 = findViewById(R.id.c_three);
        b4 = findViewById(R.id.c_four);
        b5 = findViewById(R.id.c_five);
        b6 = findViewById(R.id.c_six);
        b7 = findViewById(R.id.c_seven);
        b8 = findViewById(R.id.c_eight);
        b9 = findViewById(R.id.c_nine);
        b0 = findViewById(R.id.c_zero);
        b_ac = findViewById(R.id.c_ac);
        b_de = findViewById(R.id.c_de);
        b_add = findViewById(R.id.c_add);
        b_sub = findViewById(R.id.c_subtract);
        b_mul = findViewById(R.id.c_multiply);
        b_div = findViewById(R.id.c_divide);
        b_per = findViewById(R.id.c_percent);
        b_p = findViewById(R.id.c_point);
        b_equ = findViewById(R.id.c_equal);

        //添加监听器
        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);
        b5.setOnClickListener(listener);
        b6.setOnClickListener(listener);
        b7.setOnClickListener(listener);
        b8.setOnClickListener(listener);
        b9.setOnClickListener(listener);
        b0.setOnClickListener(listener);
        b_ac.setOnClickListener(listener);
        b_de.setOnClickListener(listener);
        b_add.setOnClickListener(listener);
        b_sub.setOnClickListener(listener);
        b_mul.setOnClickListener(listener);
        b_div.setOnClickListener(listener);
        b_per.setOnClickListener(listener);
        b_equ.setOnClickListener(listener);
        b_p.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            edit = findViewById(R.id.ed);
            text = findViewById(R.id.tv);
            Button btn = (Button) v;

            try{
                switch (btn.getId()){
                    case R.id.c_one:
                        edit.append("1");
                        opt.add('1');
                        break;
                    case R.id.c_two:
                        edit.append("2");
                        opt.add('2');
                        break;
                    case R.id.c_three:
                        edit.append("3");
                        opt.add('3');
                        break;
                    case R.id.c_four:
                        edit.append("4");
                        opt.add('4');
                        break;
                    case R.id.c_five:
                        edit.append("5");
                        opt.add('5');
                        break;
                    case R.id.c_six:
                        edit.append("6");
                        opt.add('6');
                        break;
                    case R.id.c_seven:
                        edit.append("7");
                        opt.add('7');
                        break;
                    case R.id.c_eight:
                        edit.append("8");
                        opt.add('8');
                        break;
                    case R.id.c_nine:
                        edit.append("9");
                        opt.add('9');
                        break;
                    case R.id.c_zero:
                        edit.append("0");
                        opt.add('0');
                        break;
                    case R.id.c_ac:
                        edit.setText("");
                        opt.clear();
                        text.setText("");
                        break;
                    case R.id.c_de:
                        if(!TextUtils.isEmpty(edit.getText())){
                            opt.remove(opt.size()-1);
                            String a = edit.getText().toString();
                            a = a.substring(0, a.length()-1);
                            edit.setText(a);
                            text.setText("");
                        }
                        break;
                    case R.id.c_add:
                        edit.append("+");
                        opt.add('+');
                        break;
                    case R.id.c_subtract:
                        edit.append("-");
                        opt.add('-');
                        break;
                    case R.id.c_multiply:
                        edit.append("×");
                        opt.add('*');
                        break;
                    case R.id.c_divide:
                        edit.append("÷");
                        opt.add('/');
                        break;
                    case R.id.c_percent:
                        edit.append("%");
                        opt.add('%');
                        break;
                    case R.id.c_point:
                        edit.append(".");
                        opt.add('.');
                        break;
                    case R.id.c_equal:
                        conVert(opt);
                        break;
                }
            }
            catch (Exception e){}
        }
    };

    //转后缀
    private void conVert(ArrayList<Character> operation){
        Stack<Character> operationStack = new Stack<>();
        ArrayList<String> all = new ArrayList<>();
        Character a = operation.get(0);
        int flag=0;
        if(a=='+') operation.remove(0);
        else if(a=='-'){
            operation.remove(0);
            flag=1;
        }

        for(int i=0; i<operation.size(); i++){
            Character inOperation = operation.get(i);
            if(Character.isDigit(inOperation)){
                if(i>0){
                    if(Character.isDigit(operation.get(i-1)) || operation.get(i-1) == '.'){
                        String mid = all.remove(all.size()-1)+inOperation;
                        all.add(mid);
                    }
                    else all.add(inOperation + "");
                }
                else all.add(inOperation + "");
            }

            else if(inOperation == '+' || inOperation == '-' || inOperation =='*' || inOperation == '/' || inOperation == '%'){
                if(operationStack.isEmpty() || isp(inOperation) > isp(operationStack.peek())) operationStack.push(inOperation);
                else {
                    do {
                        all.add(operationStack.pop() + "");
                    }
                    while (!operationStack.isEmpty() && isp(inOperation) <= isp(operationStack.peek()));
                    operationStack.push(inOperation);
                }
            }

            else if(inOperation == '.'){
                if(i>0){
                    if(Character.isDigit(operation.get(i-1))){
                        String mid = all.remove(all.size()-1)+inOperation;
                        all.add(mid);
                    }
                    else all.add("0" + inOperation);
                }
                else all.add("0" + inOperation);
            }
            else {
                text.setText("输入不符合规范");
                break;
            }
        }

        while(!operationStack.isEmpty()){
            all.add(operationStack.pop() + "");
        }

        text.setText(all+"");

        if(calculate(all, flag)%1 == 0)
            text.setText(calculate(all, flag).intValue()+"");
        else
            text.setText(calculate(all, flag)+"");
    }

    private Double calculate(ArrayList<String> all, int s){
        Stack<Double> numberStack = new Stack<>();
        int as=0;
        try {
            for(String ex:all){
                boolean flag = true;
                for(int i=0; i<ex.length(); i++){
                    if(!Character.isDigit(ex.charAt(i)) && ex.charAt(i)!='.'){
                        flag = false;
                        break;
                    }
                }

                if(flag == true){
                    double temp = Double.parseDouble(ex);
                    if(s==1 && as==0)
                        numberStack.push(-temp);
                    else
                        numberStack.push(temp);
                }
                else{
                    Double a, b;
                    switch (ex.charAt(0)){
                        case '+':
                            a = numberStack.pop();
                            b = numberStack.pop();
                            numberStack.push(a+b);
                            break;
                        case '-':
                            a = numberStack.pop();
                            b = numberStack.pop();
                            numberStack.push(b-a);
                            break;
                        case '*':
                            a = numberStack.pop();
                            b = numberStack.pop();
                            numberStack.push(a*b);
                            break;
                        case '/':
                            a = numberStack.pop();
                            b = numberStack.pop();
                            numberStack.push(b/a);
                            break;
                        case '%':
                            a = numberStack.pop();
                            numberStack.push(a*0.01);
                            break;
                        default:
                            break;
                    }
                }
                as++;
            }
        }
        catch (EmptyStackException e){
            text.setText("表达式错误！");
        }
        return numberStack.pop();
    }

    //判断优先级
    private int isp(Character symbol){
        switch (symbol){
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '%': return 3;

            default:
                return 0;
        }
    }
}
