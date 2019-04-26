package com.mitwpu.kitzee.teacherspet101;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDisplayAdapter extends RecyclerView.Adapter<StudentDisplayAdapter.DisplayStudentsViewHolder> {
    List<StudentEntity> studentEntityList;
    Map< String,Integer> attendance_for_current_lecture = new HashMap< String,Integer>();
    int class_id;

    // Constructor for outer class (list initialization)
    public StudentDisplayAdapter(List<StudentEntity> studentEntityList)
    {
        this.studentEntityList = studentEntityList;
    }

    @NonNull
    @Override
    public DisplayStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_student_list_item, parent, false);


        return new DisplayStudentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DisplayStudentsViewHolder holder, int position) {
        StudentEntity studentEntity = studentEntityList.get(position);


        // Add value fetched from the database instead of getRoll_no() and getName()
        holder.disp_check_box_and_roll_num.setText(studentEntity.getRoll_no());
        holder.disp_stud_name.setText(studentEntity.getName());

        holder.disp_check_box_and_roll_num.setOnCheckedChangeListener(null);

        holder.disp_check_box_and_roll_num.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    attendance_for_current_lecture.put(holder.disp_check_box_and_roll_num.getText().toString(), 1);
                    System.out.println("LALALALALLLLLLLLLAAAAAAA PRESENT "+holder.disp_check_box_and_roll_num.getText().toString()+" "+attendance_for_current_lecture.get(holder.disp_check_box_and_roll_num.getText().toString()));
                    System.out.println(attendance_for_current_lecture.size());
                }
                else
                {
                    attendance_for_current_lecture.put(holder.disp_check_box_and_roll_num.getText().toString(), 0);
                    System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZ ABSENT students"+holder.disp_check_box_and_roll_num.getText().toString()+" "+attendance_for_current_lecture.get(holder.disp_check_box_and_roll_num.getText().toString()));
                    System.out.println(attendance_for_current_lecture.size());
                }
            }
        });
//        holder.mCheckBox.setChecked(suggestion.isSelected())
    }

    @Override
    public int getItemCount() {
        return studentEntityList.size();
    }

    public Map<String,Integer> getAttendanceList()
    {
        return attendance_for_current_lecture;
    }

    public void initialiseAttendanceList(Map<String, Integer> attendance_list)
    {
        this.attendance_for_current_lecture = attendance_list;
        System.out.println("SET UP DONE");
    }

    // Inner Class
    public class DisplayStudentsViewHolder extends RecyclerView.ViewHolder{
        public CheckBox disp_check_box_and_roll_num;
        public TextView disp_stud_name;

        // Constructor for inner class (for intialization only)
        public DisplayStudentsViewHolder(@NonNull View obj){
            super(obj);
            disp_check_box_and_roll_num = (CheckBox) obj.findViewById(R.id.disp_check_box_and_roll_num);
            disp_stud_name = (TextView)obj.findViewById(R.id.disp_stud_name);
        }
    }
}
