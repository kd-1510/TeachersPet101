package com.mitwpu.kitzee.teacherspet101;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TeacherAddAdapter extends RecyclerView.Adapter<TeacherAddAdapter.StudentsAddedViewHolder> {
    List<StudentEntity> studentEntityList;

    // Constructor for outer class (list initialization)
    public TeacherAddAdapter(List<StudentEntity> studentEntityList)
    {
        this.studentEntityList = studentEntityList;
    }

    @NonNull
    @Override
    public StudentsAddedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_add_list_item, parent, false);

        return new StudentsAddedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsAddedViewHolder holder, int position) {
        StudentEntity studentEntity = studentEntityList.get(position);

        // Add value fetched from the database instead of getRoll_no() and getName()
        holder.disp_roll_num.setText(studentEntity.getRoll_no());
        holder.disp_stud_name.setText(studentEntity.getName());
    }

    @Override
    public int getItemCount() {
        return studentEntityList.size();
    }

    // Inner Class
    public class StudentsAddedViewHolder extends RecyclerView.ViewHolder{
        public TextView disp_roll_num, disp_stud_name;

        // Constructor for inner class (for intialization only)
        public StudentsAddedViewHolder(@NonNull View obj){
            super(obj);
            disp_roll_num = (TextView)obj.findViewById(R.id.disp_roll_num);
            disp_stud_name = (TextView)obj.findViewById(R.id.disp_stud_name);
        }
    }
}
