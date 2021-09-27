package com.muflihunnisa.githubuser.activity

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.muflihunnisa.githubuser.R
import com.muflihunnisa.githubuser.adapter.UserAdapter
import com.muflihunnisa.githubuser.model.Users
import com.muflihunnisa.githubuser.databinding.ActivityMainBinding
import com.muflihunnisa.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var listUser: ArrayList<Users>
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setUserAdapter()
        showProgressBar(true)
        setViewModel()
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                MainViewModel::class.java
        )
        if (mainViewModel.getListUser().value == null) {
            mainViewModel.setListUser(this, "")
        }
        mainViewModel.getListUser().observe(this, { users ->
            if (users != null) {
                listUser = users
                userAdapter.setData(listUser)
                showProgressBar(false)
            }
        })
    }

    private fun showProgressBar(b: Boolean) {
        if (b) {
            mainBinding.pbMain.visibility = View.VISIBLE
        } else {
            mainBinding.pbMain.visibility = View.GONE
        }
    }

    private fun setUserAdapter() {
        userAdapter = UserAdapter()

        mainBinding.rvMain.apply {
            setHasFixedSize(true)
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.cv_search)
        search?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                showProgressBar(true)
                mainViewModel.setListUser(this@MainActivity, "")
                return true
            }

        })
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.cv_search)?.actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "User Name"
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String): Boolean {
                showProgressBar(true)
                mainViewModel.setListUser(this@MainActivity, p0)
                closeKeyboard()
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun closeKeyboard() {
        val view : View? = this.currentFocus

        if (view != null){
            val iMM : InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            iMM.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}