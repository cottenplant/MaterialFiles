/*
 * Copyright (c) 2018 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.files.about

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import me.zhanghai.android.files.R
import me.zhanghai.android.files.databinding.AboutFragmentBinding
import me.zhanghai.android.files.ui.LicensesDialogFragment
import me.zhanghai.android.files.util.createViewIntent
import me.zhanghai.android.files.util.startActivitySafe

class AboutFragment : Fragment() {
    private lateinit var binding: AboutFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        AboutFragmentBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val sourceUri = Uri.parse(getString(R.string.distribution_source_url))
        binding.gitHubLayout.setOnClickListener {
            startActivitySafe(sourceUri.createViewIntent())
        }
        binding.licensesLayout.setOnClickListener { LicensesDialogFragment.show(this) }
        val privacyPolicyUri = getString(R.string.distribution_privacy_policy_url)
            .takeIf { it.isNotEmpty() }
            ?.let(Uri::parse)
        binding.privacyPolicyLayout.visibility =
            if (privacyPolicyUri != null) View.VISIBLE else View.GONE
        binding.privacyPolicyLayout.setOnClickListener {
            privacyPolicyUri?.let { startActivitySafe(it.createViewIntent()) }
        }
        binding.authorNameLayout.setOnClickListener {
            startActivitySafe(AUTHOR_RESUME_URI.createViewIntent())
        }
        binding.authorGitHubLayout.setOnClickListener {
            startActivitySafe(AUTHOR_GITHUB_URI.createViewIntent())
        }
        binding.authorTwitterLayout.setOnClickListener {
            startActivitySafe(AUTHOR_TWITTER_URI.createViewIntent())
        }
    }

    companion object {
        private val AUTHOR_RESUME_URI = Uri.parse("https://resume.zhanghai.me/")
        private val AUTHOR_GITHUB_URI = Uri.parse("https://github.com/zhanghai")
        private val AUTHOR_TWITTER_URI = Uri.parse("https://twitter.com/zhanghai95")
    }
}
