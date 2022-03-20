package com.example.medicalreminder.screens.medication_drug_edit_screen.presenter;

import com.example.medicalreminder.model.MedicineRepoInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;

import java.util.Map;

public class EditMedicationDrugPresenter implements EditMedicationDrugPresenterInterface {
    private EditMedicationDrugViewInterface editMedicationDrugViewInterface;
    private MedicineRepoInterface medicineRepoInterface;

    public EditMedicationDrugPresenter(EditMedicationDrugViewInterface editMedicationDrugViewInterface, MedicineRepoInterface medicineRepoInterface) {
        this.editMedicationDrugViewInterface = editMedicationDrugViewInterface;
        this.medicineRepoInterface = medicineRepoInterface;
    }

    @Override
    public void editThisMedicine(String medName, Map<String, Object> medMap) {
        medicineRepoInterface.editMedicine(medName,medMap,editMedicationDrugViewInterface);
    }
}
