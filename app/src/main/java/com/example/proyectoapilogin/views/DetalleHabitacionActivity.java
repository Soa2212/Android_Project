package com.example.proyectoapilogin.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.model.Habitacion;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.DetalleHabitacionViewModel;

public class DetalleHabitacionActivity extends AppCompatActivity {
    private DetalleHabitacionViewModel detalleHabitacionViewModel;
    private LinearLayout layoutMenuContent,viewMenuClosed,Square1,Square2,Square3,Square4,Square5,Square6;
    private TextView Temperatura,Humedad,Voltaje,Movimiento,txtPuerta,txtLuz,TextSQ1,BloqSQ1,TextSQ2,BloqSQ2,TextSQ3,BloqSQ3,TextSQ4,BloqSQ4,TextSQ5,BloqSQ5,TextSQ6,BloqSQ6;
    private SwitchCompat S1,S2,S3,S4,S5,S6;
    private ImageView Puerta,luz;
    private Button eliminar;
    Context context = this;
    private final Handler handler = new Handler();
    private int IdentifyFS;
    private final int delayMillis = 3000;

    @Override
    protected void onStart() {
        super.onStart();

        int habitacionId = getIntent().getIntExtra("habitacionId", -1);

        if (habitacionId != -1) {
            IdentifyFS = habitacionId;
            S1.setChecked(loadSwitchState("S1"));
            S2.setChecked(loadSwitchState("S2"));
            S3.setChecked(loadSwitchState("S3"));
            S4.setChecked(loadSwitchState("S4"));
            S5.setChecked(loadSwitchState("S5"));
            S6.setChecked(loadSwitchState("S6"));
        }
    }


    private String getSwitchKeyWithId(String switchKey) {
        return switchKey + "_" + IdentifyFS;
    }

    private void saveSwitchState(String switchKey, boolean isChecked) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getSwitchKeyWithId(switchKey), isChecked);
        editor.apply();
    }

    private boolean loadSwitchState(String switchKey) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        return sharedPreferences.getBoolean(getSwitchKeyWithId(switchKey), false);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_habitacion);
        int habitacionId = getIntent().getIntExtra("habitacionId", -1);

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);

        detalleHabitacionViewModel = new ViewModelProvider(this, new DetalleHabitacionViewModel.Factory(apiService)).get(DetalleHabitacionViewModel.class);

        eliminar = findViewById(R.id.btnEliminar);
        viewMenuClosed = findViewById(R.id.viewMenuClosed);
        layoutMenuContent = findViewById(R.id.layoutMenuContent);
        Temperatura = findViewById(R.id.temperatura);
        Humedad = findViewById(R.id.humedad);
        Voltaje = findViewById(R.id.voltaje);
        Puerta = findViewById(R.id.Puerta);
        luz = findViewById(R.id.luz);
        Movimiento = findViewById(R.id.movimiento);
        txtPuerta = findViewById(R.id.txtPuerta);
        txtLuz = findViewById(R.id.txtLuz);
        S1 = findViewById(R.id.S1);
        S2 = findViewById(R.id.S2);
        S3 = findViewById(R.id.S3);
        S4 = findViewById(R.id.S4);
        S5 = findViewById(R.id.S5);
        S6 = findViewById(R.id.S6);
        Square1 = findViewById(R.id.Square1);
        TextSQ1 = findViewById(R.id.TextSQ1);
        BloqSQ1 = findViewById(R.id.BloqSQ1);
        Square2 = findViewById(R.id.Square2);
        TextSQ2 = findViewById(R.id.TextSQ2);
        BloqSQ2 = findViewById(R.id.BloqSQ2);
        Square3 = findViewById(R.id.Square3);
        TextSQ3 = findViewById(R.id.TextSQ3);
        BloqSQ3 = findViewById(R.id.BloqSQ3);
        Square4 = findViewById(R.id.Square4);
        TextSQ4 = findViewById(R.id.TextSQ4);
        BloqSQ4 = findViewById(R.id.BloqSQ4);
        Square5 = findViewById(R.id.Square5);
        TextSQ5 = findViewById(R.id.TextSQ5);
        BloqSQ5 = findViewById(R.id.BloqSQ5);
        Square6 = findViewById(R.id.Square6);
        TextSQ6 = findViewById(R.id.TextSQ6);
        BloqSQ6 = findViewById(R.id.BloqSQ6);

        Drawable d1 = getResources().getDrawable(R.drawable.items_detalle_habitacion6);
        Drawable d2 = getResources().getDrawable(R.drawable.items_detalle_habitacion);
        int colorTexto1 = ContextCompat.getColor(context, R.color.gray);
        int colorTexto2 = ContextCompat.getColor(context, R.color.black);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetalleHabitacionActivity.this)
                        .setTitle("Eliminar Habitación")
                        .setMessage("¿Estás seguro de que quieres eliminar esta habitación?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Llama a la función eliminarHabitacion
                                detalleHabitacionViewModel.eliminarHabitacion(DetalleHabitacionActivity.this, habitacionId);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        detalleHabitacionViewModel.getIsDeleteSuccessful().observe(this, isSuccessful -> {
            if (isSuccessful) {
                new AlertDialog.Builder(DetalleHabitacionActivity.this)
                        .setTitle("Habitación eliminada")
                        .setMessage("La habitación ha sido eliminada exitosamente.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(DetalleHabitacionActivity.this, Recycler.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
            }
        });

        S1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    S1.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.thumn_on));
                    Square1.setBackground(d1);
                    TextSQ1.setTextColor(colorTexto1);
                    Temperatura.setTextColor(colorTexto1);
                    BloqSQ1.setVisibility(View.VISIBLE);

                } else {
                    S1.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.track));
                    Square1.setBackground(d2);
                    TextSQ1.setTextColor(colorTexto2);
                    Temperatura.setTextColor(colorTexto2);
                    BloqSQ1.setVisibility(View.INVISIBLE);


                }
                saveSwitchState("S1", isChecked);
            }
        });
        S2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    S2.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.thumn_on));
                    Square2.setBackground(d1);
                    TextSQ2.setTextColor(colorTexto1);
                    Humedad.setTextColor(colorTexto1);
                    BloqSQ2.setVisibility(View.VISIBLE);
                } else {
                    S2.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.track));
                    Square2.setBackground(d2);
                    TextSQ2.setTextColor(colorTexto2);
                    Humedad.setTextColor(colorTexto2);
                    BloqSQ2.setVisibility(View.INVISIBLE);

                }
                saveSwitchState("S2", isChecked);
            }
        });
        S3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    S3.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.thumn_on));
                    Square3.setBackground(d1);
                    TextSQ3.setTextColor(colorTexto1);
                    Voltaje.setTextColor(colorTexto1);
                    BloqSQ3.setVisibility(View.VISIBLE);
                } else {
                    S3.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.track));
                    Square3.setBackground(d2);
                    TextSQ3.setTextColor(colorTexto2);
                    Voltaje.setTextColor(colorTexto2);
                    BloqSQ3.setVisibility(View.INVISIBLE);

                }
                saveSwitchState("S3", isChecked);

            }
        });
        S4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    S4.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.thumn_on));
                    Square4.setBackground(d1);
                    TextSQ4.setTextColor(colorTexto1);
                    Puerta.setVisibility(View.INVISIBLE);
                    BloqSQ4.setVisibility(View.VISIBLE);
                    txtPuerta.setVisibility(View.INVISIBLE);

                } else {
                    S4.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.track));
                    Square4.setBackground(d2);
                    TextSQ4.setTextColor(colorTexto2);
                    Puerta.setVisibility(View.VISIBLE);
                    BloqSQ4.setVisibility(View.INVISIBLE);
                    txtPuerta.setVisibility(View.VISIBLE);

                }
                saveSwitchState("S4", isChecked);

            }
        });
        S5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    S5.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.thumn_on));
                    Square5.setBackground(d1);
                    TextSQ5.setTextColor(colorTexto1);
                    luz.setVisibility(View.INVISIBLE);
                    BloqSQ5.setVisibility(View.VISIBLE);
                    txtLuz.setVisibility(View.INVISIBLE);

                } else {
                    S5.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.track));
                    Square5.setBackground(d2);
                    TextSQ5.setTextColor(colorTexto2);
                    luz.setVisibility(View.VISIBLE);
                    BloqSQ5.setVisibility(View.INVISIBLE);
                    txtLuz.setVisibility(View.VISIBLE);
                }
                saveSwitchState("S5", isChecked);

            }
        });
        S6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    S6.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.thumn_on));
                    Square6.setBackground(d1);
                    TextSQ6.setTextColor(colorTexto1);
                    Movimiento.setTextColor(colorTexto1);
                    BloqSQ6.setVisibility(View.VISIBLE);

                } else {
                    S6.getTrackDrawable().setTint(ContextCompat.getColor(context, R.color.track));
                    Square6.setBackground(d2);
                    TextSQ6.setTextColor(colorTexto2);
                    Movimiento.setTextColor(colorTexto2);
                    BloqSQ6.setVisibility(View.INVISIBLE);
                }
                saveSwitchState("S6", isChecked);

            }
        });


        layoutMenuContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });
        viewMenuClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });

        fetchAndUpdateData(habitacionId);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchAndUpdateData(habitacionId);
                handler.postDelayed(this, delayMillis);
            }
        }, delayMillis);

    }

    private void fetchAndUpdateData(int habitacionId) {
        detalleHabitacionViewModel.fetchHabitacionById(habitacionId);
        detalleHabitacionViewModel.getHabitacion().observe(this, habitacion -> {
            Temperatura.setText(String.valueOf(habitacion.getTemperatura())+"°");
            Humedad.setText(String.valueOf(habitacion.getHumedad())+"%");
            Voltaje.setText(String.valueOf(habitacion.getVoltaje())+" V");
            if(habitacion.getLuz()==1){
                luz.setImageResource(R.drawable.foco_1);
                txtLuz.setText("Encendido");
            }
            else{
                luz.setImageResource(R.drawable.foco_0);
                txtLuz.setText("Apagado");

            }
            if(habitacion.getSensorMagnetico()==1){
                Puerta.setImageResource(R.drawable.candado_1);
                txtPuerta.setText("Cerrado");

            }
            else{
                Puerta.setImageResource(R.drawable.candado_0);
                txtPuerta.setText("Abierto");
            }
            Movimiento.setText(String.valueOf(habitacion.getMovimiento()));
        });
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void toggleMenu() {
        if (layoutMenuContent.getVisibility() == View.VISIBLE) {
            collapseMenu();
        } else {
            expandMenu();
        }
    }
    private void expandMenu() {
        layoutMenuContent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        final int targetWidth = layoutMenuContent.getMeasuredWidth();

        layoutMenuContent.setVisibility(View.GONE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                layoutMenuContent.getLayoutParams().width = (int) (targetWidth * interpolatedTime);
                layoutMenuContent.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(500);
        layoutMenuContent.startAnimation(animation);

        layoutMenuContent.setVisibility(View.VISIBLE);
    }
    private void collapseMenu() {
        final int initialWidth = layoutMenuContent.getMeasuredWidth();
        viewMenuClosed.setVisibility(View.VISIBLE);
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    layoutMenuContent.setVisibility(View.INVISIBLE);
                } else {
                    layoutMenuContent.getLayoutParams().width = initialWidth - (int) (initialWidth * interpolatedTime);
                    layoutMenuContent.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(500);
        layoutMenuContent.startAnimation(animation);
    }
}

