package com.julius.mytube.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.julius.mytube.R
import com.julius.mytube.databinding.FragmentSearchBinding
import com.julius.mytube.extends.inflate
import com.julius.mytube.viewmodels.main.TopNavigationViewModel

class SearchFragment : DialogFragment() {
    private val topNavigationViewModel by activityViewModels<TopNavigationViewModel>()
    private val args by navArgs<SearchFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflate<FragmentSearchBinding>(inflater, container).apply {
            lifecycleOwner = viewLifecycleOwner
            topNavigationViewModel = this@SearchFragment.topNavigationViewModel
                .apply {
                    onSearch(viewLifecycleOwner) {
                        findNavController().navigateUp()
                    }
                }
            position = args.position
            toolsBar.setupWithNavController(findNavController())
        }.root
    }
}