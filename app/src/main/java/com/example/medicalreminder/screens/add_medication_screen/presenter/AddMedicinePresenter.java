package com.example.medicalreminder.screens.add_medication_screen.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.medicalreminder.model.MedicineRepo;
import com.example.medicalreminder.model.MedicineRepoInterface;
import com.example.medicalreminder.pojo.DoseTime;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineNotification;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicineViewInterface;

import java.util.List;
import java.util.Map;

public class AddMedicinePresenter implements AddMedicinePresenterInterface {
    private AddMedicineViewInterface addMedicineViewInterface;
    private MedicineRepoInterface medicineRepoInterface;

    public AddMedicinePresenter(AddMedicineViewInterface addMedicineViewInterface, MedicineRepoInterface medicineRepoInterface) {
        this.addMedicineViewInterface = addMedicineViewInterface;
        this.medicineRepoInterface = medicineRepoInterface;
    }


    @Override
    public void addMedicine(Medicine medicine, MedicineNotification medicineNotification) {
        medicineRepoInterface.addMedicine(medicine, medicineNotification, addMedicineViewInterface);
    }

    @Override
    public void addMedicineToDatabase(Medicine medicine,MedicineNotification medicineNotification) {
        for (Map.Entry<String, List<DoseTime>> entry : medicine.getMedTimeDosesPerDay().entrySet()) {
            medicineNotification.setDate(entry.getKey());

            MedicineNotification m = new MedicineNotification();
            m.setMedicineName(medicineNotification.getMedicineName());
            m.setDate(entry.getKey());
            m.setStrenthUnit(medicineNotification.getStrenthUnit());
            m.setStrength(medicineNotification.getStrength());
            m.setMedLastTakenTime("");
            m.setMedLastTakenDate("");
            m.setInstruction("Before");
            m.setUserName("Marwan");

            for (int i = 0; i < entry.getValue().size(); i++) {
                m.setTime(entry.getValue().get(i).getHour()+":"+entry.getValue().get(i).getMinute());
                //medicineNotification.setTime(entry.getValue().get(i).getHour() + ":" + entry.getValue().get(i).getMinute());
                Log.e(MedicineRepo.class.getSimpleName(),"Bye0 : "+entry.getKey());
                Log.e(MedicineRepo.class.getSimpleName(),"Bye0 : "+entry.getValue().size());
                medicineRepoInterface.addMedicineToDatabase(m,addMedicineViewInterface);
            }
        }
    }

    @Override
    public void getTodayNotification(String date, LifecycleOwner lifecycleOwner) {
        medicineRepoInterface.getTodayNotificationFromRoom(date).observe(lifecycleOwner, new Observer<List<MedicineNotification>>() {
            @Override
            public void onChanged(List<MedicineNotification> medicineNotifications) {
                addMedicineViewInterface.notifyAddFromDatabase(medicineNotifications);
            }
        });

    }

}
