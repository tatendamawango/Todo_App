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
import edu.ktu.firstapp.R
import edu.ktu.firstapp.models.Task
import edu.ktu.firstapp.adapters.TaskAdapter
import edu.ktu.firstapp.databinding.FragmentTasksBinding

class TasksFragment : Fragment(), TaskAdapter.TodoAdapterClickListener {

    private lateinit var navController: NavController
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var taskList: MutableList<Task>
    private lateinit var databaseref: DatabaseReference
    private lateinit var binding: FragmentTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
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
                taskAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseref = FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        taskList = mutableListOf()
        taskAdapter = TaskAdapter(taskList)
        taskAdapter.setListener(this)
        binding.tasksRecyclerView.adapter = taskAdapter
    }

    override fun onEditClick(task: Task) {
        navController.navigate(R.id.action_tasksFragment_to_editTaskFragment)
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
        val sourceRef = FirebaseDatabase.getInstance().getReference("Tasks").child(task.taskid)
        sourceRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val task = snapshot.getValue(Task::class.java)
                if (task != null) {
                    val destinationRef = FirebaseDatabase.getInstance().getReference("InProgress").child(task.taskid)
                    destinationRef.setValue(task).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Task moved to In Progress", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Task not moved to In Progress", Toast.LENGTH_SHORT).show()
                        }
                    }
                    sourceRef.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onFinishedClick(task: Task) {
//        val sourceRef = FirebaseDatabase.getInstance().getReference("Tasks").child(task.taskid)
//        sourceRef.addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val task = snapshot.getValue(Task::class.java)
//                if (task != null) {
//                    val destinationRef = FirebaseDatabase.getInstance().getReference("Finished").child(task.taskid)
//                    destinationRef.setValue(task).addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            Toast.makeText(context, "Task moved to Finished", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(context, "Task not moved to Finished", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                    sourceRef.removeValue()
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//            }
//        })
    }


}