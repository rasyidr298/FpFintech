package id.fp.fintech.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.fp.fintech.ui.main.home.HomeFragment
import id.fp.fintech.ui.main.markets.MarketsFragment
import id.fp.fintech.ui.main.wallet.WalletFragment

class BottomNavPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> MarketsFragment()
            else -> WalletFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
