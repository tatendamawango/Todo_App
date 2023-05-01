package edu.ktu.firstapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import edu.ktu.firstapp.adapters.FinishedAdapter
import edu.ktu.firstapp.R
import edu.ktu.firstapp.models.Task
import edu.ktu.firstapp.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment(), FinishedAdapter.FinishedAdapterClickListener {
    private lateinit var navController: NavController
    private lateinit var adapter: FinishedAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var taskList: MutableList<Task>
    private lateinit var databaseref: DatabaseReference
    private lateinit var binding: FragmentFinishedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getDataFromFirebase()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseref = FirebaseDatabase.getInstance().reference
            .child("Finished").child(auth.currentUser?.uid.toString())
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        taskList = mutableListOf()
        adapter = FinishedAdapter(taskList)
        adapter.setListener(this)
        binding.tasksRecyclerView.adapter = adapter
    }

    private fun getDataFromFirebase() {
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                taskList.clear()
                for (data in snapshot.children) {
                    val task = data.key?.let {
                        Task(it, data.child("description").value.toString(), data.child("date").value.toString())
                    }
                    if (task != null) {
                        taskList.add(task)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onEditClick(task: Task) {
        navController.navigate(R.id.action_finishedFragment_to_editTaskFragment)
    }

    override fun onDeleteClick(task: Task) {
        databaseref.child(task.taskid).removeValue().addOnCompleteListener{
            if (it.isSuccessful) {
                Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Task not deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInProgressClick(task: Task) {
//        val sourceRef = FirebaseDatabase.getInstance().getReference("Finished").child(task.taskid)
//        sourceRef.addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val task = snapshot.getValue(Task::class.java)
//                if (task != null) {
//                    val destinationRef = FirebaseDatabase.getInstance().getReference("InProgress").child(task.taskid)
//                    destinationRef.setValue(task).addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            Toast.makeText(context, "Task moved to In Progress", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(context, "Task not moved to In Progress", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    override fun onTaskClick(task: Task) {
//        val sourceRef = FirebaseDatabase.getInstance().getReference("Finished").child(task.taskid)
//        sourceRef.addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val task = snapshot.getValue(Task::class.java)
//                if (task != null) {
//                    val destinationRef = FirebaseDatabase.getInstance().getReference("Tasks").child(task.taskid)
//                    destinationRef.setValue(task).addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            Toast.makeText(context, "Task moved to Tasks", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(context, "Task not moved to Tasks", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//            }
//        })
    }
}