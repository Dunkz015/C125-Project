package com.example.zcalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity<v> extends AppCompatActivity {
    private float textsize = 16;
    private boolean degree = true;
    public boolean equals = false; // verifica se o input é nulo
    Button Help; // botão de ajuda
    private Complex ANS = null; // auxiliar para armanezar o último resultado
    private EditText textViewOut;
    private EditText textView;
    /*------------------------------------------------------------------------*/
    // função para chamar uma instância
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.impedance_calculator_activity);

        textViewOut = (EditText) findViewById(R.id.txtOut);
        textViewOut.setKeyListener(null);

        textView = (EditText) findViewById(R.id.txt);
        textView.setShowSoftInputOnFocus(false);

        Help = (Button) findViewById(R.id.Help); // instanciamento do botão
        Help.setOnClickListener(new View.OnClickListener() { // função para o click do botão
            @Override
            public void onClick(View v) { // clica
                Intent intent = new Intent(MainActivity.this, Ajuda.class);
                startActivity(intent); // acontece
            }
        });
    }

    //Auxiliary method:
    public void cursor(String string){
        if (textView.getSelectionStart() != textView.getText().length()) {
            textView.getText().insert(textView.getSelectionStart(), string);
            equals = false;
        }
        else if (equals) {
            textView.setText(string);
            textView.setSelection(textView.getText().length());
            equals = false;
        }
        else
            textView.getText().insert(textView.getSelectionStart(), string);
    }

    /*------------------------------------------------------------------------*/
    // funções para o uso dos botões

    @SuppressLint("SetTextI18n")
    public void moveleft(View view) {
        textView = (EditText) findViewById(R.id.txt);
        try {
            textView.setSelection(textView.getSelectionStart() - 1);
        }catch (Exception ignored){}
    }

    @SuppressLint("SetTextI18n")
    public void moveright(View view) {
        textView = (EditText) findViewById(R.id.txt);
        try {
            textView.setSelection(textView.getSelectionStart() + 1);
        }catch (Exception ignored){}
    }

    @SuppressLint("SetTextI18n")
    public void setunit(View view) {
        textView = (EditText) findViewById(R.id.txt);
        Button button = (Button) view;
        if (degree) {
            Complex.setPolarunit(1);
            button.setText("Rad");
            degree = false;
        }else{
            button.setText("Deg");
            Complex.setPolarunit(57.296);
            degree = true;
        }
    }

    @SuppressLint("SetTextI18n")
    public void zoomin(View view) {
        textView = (EditText) findViewById(R.id.txt);
        if(textsize == 24)
            Toast.makeText(getApplicationContext(), "That's the maximum size", Toast.LENGTH_SHORT).show();
        else {
            textsize+=2;
            textView.setTextSize(textsize);
            textViewOut.setTextSize(textsize);
        }
    }

    @SuppressLint("SetTextI18n")
    public void zoomout(View view) {
        textView = (EditText) findViewById(R.id.txt);
        if(textsize == 12)
            Toast.makeText(getApplicationContext(), "That's the minimum size", Toast.LENGTH_SHORT).show();
        else {
            textsize-=2;
            textView.setTextSize(textsize);
            textViewOut.setTextSize(textsize);
        }
    }

    @SuppressLint("SetTextI18n")
    public void del(View view) {
        textView = (EditText) findViewById(R.id.txt);
        try {
            String str, str2;
            str = textView.getText().toString();
            str2 = textView.getText().toString();
            char[] input = str.toCharArray();
            if (equals) {
                if (textView.getSelectionStart() != textView.getText().length()) {
                    if (textView.getSelectionStart() - 2 > 0 && input[textView.getSelectionStart() - 2] == '|') { // para o paralelo
                        str = str.substring(0, textView.getSelectionStart() - 2);
                        str2 = str2.substring(textView.getSelectionStart());
                        textView.setText(str + str2);
                        textView.setSelection(str.length());
                    }
                    else if (textView.getSelectionStart() != textView.length() && input[textView.getSelectionStart() - 1] == '|'){
                        str = str.substring(0, textView.getSelectionStart() - 1);
                        str2 = str2.substring(textView.getSelectionStart());
                        str2 = str2.substring(1);
                        textView.setText(str + str2);
                        textView.setSelection(str.length());
                    }
                    else {
                        str = str.substring(0, textView.getSelectionStart() - 1);
                        str2 = str2.substring(textView.getSelectionStart());
                        textView.setText(str + str2);
                        textView.setSelection(str.length());
                    }
                    equals = false;
                }
                else {
                    textView.setText(null);
                    equals = false;
                }
            } else {
                if(Objects.equals(textView.getText().toString(), "||") &&
                        (textView.getSelectionStart() == str.length() ||
                                textView.getSelectionStart() == str.length() -1)) {
                    str = "";
                    str2 = "";
                    textView.setText(str + str2);
                }
                else if (textView.getSelectionStart() - 2 > 0 && input[textView.getSelectionStart() - 2] == '|') { // para o paralelo
                    str = str.substring(0, textView.getSelectionStart() - 2);
                    str2 = str2.substring(textView.getSelectionStart());
                    textView.setText(str + str2);
                    textView.setSelection(str.length());
                }
                else if (textView.getSelectionStart() != textView.length() && input[textView.getSelectionStart() - 1] == '|'){
                    str = str.substring(0, textView.getSelectionStart() - 1);
                    str2 = str2.substring(textView.getSelectionStart());
                    str2 = str2.substring(1);
                    textView.setText(str + str2);
                    textView.setSelection(str.length());
                }
                else {
                    str = str.substring(0, textView.getSelectionStart() - 1);
                    str2 = str2.substring(textView.getSelectionStart());
                    textView.setText(str + str2);
                    textView.setSelection(str.length());
                }
            }
        } catch (Exception ignored) {}
    }

    @SuppressLint("SetTextI18n")
    public void ans(View view) {
        textView = (EditText) findViewById(R.id.txt);
        String str, str2, aux;
        str = textView.getText().toString();
        str2 = textView.getText().toString();
        if (ANS != null) {
            aux = ANS.getReal() + "i" + ANS.getImaginary();
            if (equals) {
                if (textView.getSelectionStart() != textView.getText().length()) {
                    str = str.substring(0, textView.getSelectionStart());
                    str2 = str2.substring(textView.getSelectionStart());
                    textView.setText(str + aux + str2);
                    textView.setSelection(aux.length() + str.length());
                    equals = false;
                }
                else {
                    textView.setText(aux);
                    textView.setSelection(aux.length());
                    equals = false;
                }
            } else {
                str = str.substring(0, textView.getSelectionStart());
                str2 = str2.substring(textView.getSelectionStart());
                textView.setText(str + aux + str2);
                textView.setSelection(aux.length() + str.length());
            }
        }
    }

    public void AC(View view) {
        textView = (EditText) findViewById(R.id.txt);
        textView.setText(null);
        textViewOut.setText(null);
        textView.setSelection(textView.getText().length());
        equals = false;
    }

    @SuppressLint("SetTextI18n")
    public void L(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("L");
    }

    @SuppressLint("SetTextI18n")
    public void C(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("C");
    }

    @SuppressLint("SetTextI18n")
    public void paralel(View view) {
        textView = (EditText) findViewById(R.id.txt);
        if (equals) {
            if (textView.getSelectionStart() != textView.getText().length()) {
                textView.getText().insert(textView.getSelectionStart(), "||");
            }
            else if (equals) {
                textView.setText(ANS.getReal() + "i" + ANS.getImaginary() + "||");
                textView.setSelection(textView.getText().length());
            }
            equals = false;
        } else {
            cursor("||");
        }
    }

    @SuppressLint("SetTextI18n")
    public void serie(View view) {
        textView = (EditText) findViewById(R.id.txt);
        if (equals) {
            if (textView.getSelectionStart() != textView.getText().length()) {
                textView.getText().insert(textView.getSelectionStart(), "+");
            }
            else if (equals) {
                textView.setText(ANS.getReal() + "i" + ANS.getImaginary() + "+");
                textView.setSelection(textView.getText().length());
            }
            equals = false;
        } else {
            cursor("+");
        }
    }

    @SuppressLint("SetTextI18n")
    public void minus(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("-");
    }

    @SuppressLint("SetTextI18n")
    public void pico(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("p");
    }

    @SuppressLint("SetTextI18n")
    public void nano(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("n");
    }

    @SuppressLint("SetTextI18n")
    public void micro(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("μ");
    }

    @SuppressLint("SetTextI18n")
    public void mili(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("m");
    }

    @SuppressLint("SetTextI18n")
    public void kilo(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("k");
    }

    @SuppressLint("SetTextI18n")
    public void mega(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("M");
    }

    @SuppressLint("SetTextI18n")
    public void giga(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("G");
    }

    @SuppressLint("SetTextI18n")
    public void um(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("1");
    }

    @SuppressLint("SetTextI18n")
    public void dois(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("2");
    }

    @SuppressLint("SetTextI18n")
    public void tres(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("3");
    }

    @SuppressLint("SetTextI18n")
    public void quatro(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("4");
    }

    @SuppressLint("SetTextI18n")
    public void cinco(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("5");
    }

    @SuppressLint("SetTextI18n")
    public void seis(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("6");
    }

    @SuppressLint("SetTextI18n")
    public void sete(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("7");
    }

    @SuppressLint("SetTextI18n")
    public void oito(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("8");
    }

    @SuppressLint("SetTextI18n")
    public void nove(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("9");
    }

    @SuppressLint("SetTextI18n")
    public void zero(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("0");
    }

    @SuppressLint("SetTextI18n")
    public void ponto(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor(".");
    }

    @SuppressLint("SetTextI18n")
    public void parent2(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor(")");
    }

    @SuppressLint("SetTextI18n")
    public void parent1(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("(");
    }

    @SuppressLint("SetTextI18n")
    public void i(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("i");
    }

    @SuppressLint("SetTextI18n")
    public void E(View view) {
        textView = (EditText) findViewById(R.id.txt);
        cursor("E");
    }

    @SuppressLint("SetTextI18n")
    public void equals(View view) {
        textView = (EditText) findViewById(R.id.txt);
        textViewOut = (EditText) findViewById(R.id.txtOut);
        textViewOut.setTextColor(Color.parseColor("#FFFFFFFF"));
        try {
            if (!textView.getText().toString().equals("")) {
                ANS = EvaluateInput.evaluate(textView.getText().toString());
                textViewOut.setText(ANS.toString() + "\n" + Complex.polar(ANS));
                equals = true;
            }
            textView.setSelection(textView.getText().length());
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Invalid Expression", Toast.LENGTH_SHORT).show();
            textViewOut.setTextColor(Color.parseColor("#FF0000"));
            textViewOut.setText("Invalid expression");
        }
    }
    /*------------------------------------------------------------------------*/
}