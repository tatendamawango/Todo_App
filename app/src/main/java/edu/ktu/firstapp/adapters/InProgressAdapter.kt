package edu.ktu.firstapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ktu.firstapp.R
import edu.ktu.firstapp.models.Task


class InProgressAdapter(private val taskList: List<Task>)
    : RecyclerView.Adapter<InProgressAdapter.ViewHolder>(){

    private var listener: InProgressAdapterClickListener? = null
    fun setListener(listener: InProgressAdapterClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val description: TextView = view.findViewById(R.id.descriptiontv)
        val date: TextView = view.findViewById(R.id.datetv)

        private val overflowButton: ImageButton = itemView.findViewById(R.id.moreBtn)
        init {
            overflowButton.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, overflowButton)
                popupMenu.menuInflater.inflate(R.menu.inprogress_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.editOption -> {
                            listener?.onEditClick(taskList[adapterPosition])
                            true
                        }
                        R.id.deleteOption -> {
                            listener?.onDeleteClick(taskList[adapterPosition])
                            true
                        }
                        R.id.tasksOption -> {
                            listener?.onTasksClick(taskList[adapterPosition])
                            true
                        }
                        R.id.finishedOption -> {
                            listener?.onFinishedClick(taskList[adapterPosition])
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_task, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = taskList[position]
        holder.description.text = data.description
        holder.date.text = data.date
    }

    interface InProgressAdapterClickListener {
        fun onEditClick(task: Task)
        fun onDeleteClick(task: Task)
        fun onTasksClick(task: Task)
        fun onFinishedClick(task: Task)
    }

}