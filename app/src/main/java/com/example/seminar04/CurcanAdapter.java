package com.example.seminar04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CurcanAdapter extends ArrayAdapter<Curcani> {
    private final Context context;
    private final int resource;

    public CurcanAdapter(@NonNull Context context, int resource, @NonNull List<Curcani> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Curcani curcan = getItem(position);

        if (curcan != null) {
            TextView tvRasa = convertView.findViewById(R.id.tvRowRasa);
            TextView tvDetalii = convertView.findViewById(R.id.tvRowDetalii);
            TextView tvSanatate = convertView.findViewById(R.id.tvRowSanatate);

            tvRasa.setText(curcan.getRasa());

            String detalii = "Matur: " + (curcan.isEsteMatur() ? "Da" : "Nu") +
                    " | Hrană: " + curcan.getTipHrana() +
                    " | Gen: " + (curcan.isEsteMasculin() ? "Mascul" : "Femelă");
            tvDetalii.setText(detalii);

            String sanatate = "Sănătate: " + curcan.getNotaSanatate() + "/5 ★ | Vaccinat: " + (curcan.isEsteVaccinat() ? "Da" : "Nu");
            tvSanatate.setText(sanatate);
        }

        return convertView;
    }
}
