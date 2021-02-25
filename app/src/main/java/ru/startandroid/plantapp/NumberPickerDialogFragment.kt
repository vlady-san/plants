package ru.startandroid.plantapp

import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult


class NumberPickerDialogFragment : DialogFragment() {

    companion object
    {
        fun newInstance(activity: String): NumberPickerDialogFragment {
            val args = Bundle()
            args.putString("activity", activity)

            val fragment = NumberPickerDialogFragment()
            fragment.arguments = args
            return fragment
        }
        val NUMBER_PICKER = "selected number"
        val ACTIVITY_PICKER = "selected activity"
    }

    private var selectedNumber : Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var activity = arguments?.getString("activity", "")
            val builder = AlertDialog.Builder(it)
            var view = layoutInflater.inflate(R.layout.number_picker_dialog, null)
            var picker = view.findViewById<NumberPicker>(R.id.outlet_picker)
            picker.minValue = 0
            picker.maxValue = 50
            picker.wrapSelectorWheel = false
            picker.setOnValueChangedListener { _, _, newVal ->
                selectedNumber = newVal
            }
            builder.setView(view);
            builder
                .setPositiveButton(
                    "Да"
                ) { dialog, id ->
                    setFragmentResult(NUMBER_PICKER, bundleOf(NUMBER_PICKER to selectedNumber, ACTIVITY_PICKER to activity))
                    dialog.cancel()

                }
                .setNegativeButton("Отмена") { dialog, id ->
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}