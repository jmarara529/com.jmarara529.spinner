package com.jmarara529.spinner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText textNumber1 = findViewById(R.id.TextNumber1);
        EditText textNumber2 = findViewById(R.id.TextNumber2);
        TextView resultado = findViewById(R.id.resultado);
        Spinner spinner = findViewById(R.id.spinner);

        // Obtener el array de cadenas desde los recursos
        String[] items = getResources().getStringArray(R.array.item);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.position = position;
                calcularResultado(textNumber1, textNumber2, resultado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                resultado.setText(R.string.result);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                calcularResultado(textNumber1, textNumber2, resultado);
            }
        };

        textNumber1.addTextChangedListener(textWatcher);
        textNumber2.addTextChangedListener(textWatcher);
    }

    private void calcularResultado(EditText textNumber1, EditText textNumber2, TextView resultado) {
        if (position == 0) {
            resultado.setText(R.string.result);
            return;
        }

        if (textNumber1.getText().toString().isEmpty() || textNumber2.getText().toString().isEmpty()) {
            resultado.setText(R.string.nonum);
            return;
        }

        int num1 = Integer.parseInt(textNumber1.getText().toString());
        int num2 = Integer.parseInt(textNumber2.getText().toString());

        int suma = num1 + num2;
        int resta = num1 - num2;
        int mult = num1 * num2;
        String resultadoTexto = "";

        switch (position) {
            case 0: // Seleccione una opción
                resultadoTexto = getString(R.string.result);
                break;
            case 1: // Suma
                resultadoTexto = getString(R.string.result) + " " + suma;
                break;
            case 2: // Resta
                resultadoTexto = getString(R.string.result) + " " + resta;
                break;
            case 3: // Multiplicación
                resultadoTexto = getString(R.string.result) + " " + mult;
                break;
            case 4: // División
                if (num2 != 0) {
                    int divi = num1 / num2;
                    resultadoTexto = getString(R.string.result) + " " + divi;
                } else {
                    resultadoTexto = "Error: División por cero";
                }
                break;
        }
        resultado.setText(resultadoTexto);
    }
}
