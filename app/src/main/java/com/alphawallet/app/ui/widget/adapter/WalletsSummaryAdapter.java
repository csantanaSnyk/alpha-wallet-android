package com.alphawallet.app.ui.widget.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.alphawallet.app.R;
import com.alphawallet.app.entity.Wallet;
import com.alphawallet.app.entity.WalletType;
import com.alphawallet.app.interact.GenericWalletInteract;
import com.alphawallet.app.ui.widget.entity.WalletClickCallback;
import com.alphawallet.app.ui.widget.holder.BinderViewHolder;
import com.alphawallet.app.ui.widget.holder.TextHolder;
import com.alphawallet.app.ui.widget.holder.WalletHolder;
import com.alphawallet.app.ui.widget.holder.WalletSummaryHeaderHolder;
import com.alphawallet.app.ui.widget.holder.WalletSummaryHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.realm.Realm;

public class WalletsSummaryAdapter extends RecyclerView.Adapter<BinderViewHolder> implements WalletClickCallback
{
    private final OnSetWalletDefaultListener onSetWalletDefaultListener;
    private final ArrayList<Wallet> wallets;
    private Wallet defaultWallet = null;
    private final Context context;
    private final Realm realm;

    public WalletsSummaryAdapter(Context ctx,
                                 OnSetWalletDefaultListener onSetWalletDefaultListener, GenericWalletInteract genericWalletInteract) {
        this.onSetWalletDefaultListener = onSetWalletDefaultListener;
        this.wallets = new ArrayList<>();
        this.context = ctx;
        this.realm = genericWalletInteract.getWalletRealm();
    }

    @NotNull
    @Override
    public BinderViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        BinderViewHolder binderViewHolder = null;
        switch (viewType) {
            case WalletHolder.VIEW_TYPE:
                binderViewHolder = new WalletSummaryHolder(R.layout.item_wallet_summary_manage, parent, this, realm);
            break;
            case TextHolder.VIEW_TYPE:
                binderViewHolder = new TextHolder(R.layout.item_text_view, parent);
                break;
            case WalletSummaryHeaderHolder.VIEW_TYPE:
                binderViewHolder = new WalletSummaryHeaderHolder(R.layout.item_wallet_summary_large_title, parent,this, realm);
                break;
            default:
                break;
        }
        return binderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull BinderViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case WalletHolder.VIEW_TYPE:
                Wallet wallet = wallets.get(position);
                Bundle bundle = new Bundle();
                bundle.putBoolean(
                        WalletHolder.IS_DEFAULT_ADDITION,
                        defaultWallet != null && defaultWallet.sameAddress(wallet.address));
                bundle.putBoolean(WalletHolder.IS_LAST_ITEM, getItemCount() == 1);
                holder.bind(wallet, bundle);
                break;
            case TextHolder.VIEW_TYPE:
                wallet = wallets.get(position);
                holder.bind(wallet.address);
                break;
            case WalletSummaryHeaderHolder.VIEW_TYPE:
                wallet = defaultWallet;
                holder.bind(wallet);
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (wallets.get(position).type)
        {
            default:
            case WATCH:
            case KEYSTORE:
            case KEYSTORE_LEGACY:
            case HDKEY:
                return WalletHolder.VIEW_TYPE;
            case TEXT_MARKER:
                return TextHolder.VIEW_TYPE;
            case LARGE_TITLE:
                return WalletSummaryHeaderHolder.VIEW_TYPE;
        }
    }

    public void setDefaultWallet(Wallet wallet) {
        this.defaultWallet = wallet;
        notifyDataSetChanged();
    }

    public void setWallets(Wallet[] wallets)
    {
        this.wallets.clear();
        boolean hasLegacyWallet = false;
        boolean hasWatchWallet = false;
        if (wallets != null)
        {
            Wallet summaryItem = new Wallet(context.getString(R.string.summary));
            summaryItem.type = WalletType.TEXT_MARKER;
            this.wallets.add(summaryItem);

            Wallet largeTitle = new Wallet(context.getString(R.string.summary));
            largeTitle.type = WalletType.LARGE_TITLE;
            this.wallets.add(largeTitle);

            Wallet yourWallets = new Wallet(context.getString(R.string.your_wallets));
            yourWallets.type = WalletType.TEXT_MARKER;
            this.wallets.add(yourWallets);

            //Add HD Wallets
            for (Wallet w : wallets)
            {
                switch (w.type)
                {
                    case KEYSTORE_LEGACY:
                    case KEYSTORE:
                        hasLegacyWallet = true;
                        break;
                    case HDKEY:
                        this.wallets.add(w);
                        break;
                    case WATCH:
                        hasWatchWallet = true;
                        break;
                    default:
                        break;
                }
            }

            if (hasLegacyWallet)
            {
                Wallet legacyText = new Wallet(context.getString(R.string.legacy_wallets));
                legacyText.type = WalletType.TEXT_MARKER;
                this.wallets.add(legacyText);

                for (Wallet w : wallets)
                {
                    if (w.type == WalletType.KEYSTORE || w.type == WalletType.KEYSTORE_LEGACY)
                    {
                        this.wallets.add(w);
                    }
                }
            }

            if (hasWatchWallet)
            {
                Wallet watchText = new Wallet(context.getString(R.string.watch_wallet));
                watchText.type = WalletType.TEXT_MARKER;
                this.wallets.add(watchText);

                for (Wallet w : wallets)
                {
                    if (w.type == WalletType.WATCH)
                    {
                        this.wallets.add(w);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onWalletClicked(Wallet wallet)
    {
        onSetWalletDefaultListener.onSetDefault(wallet);
    }

    public void onDestroy()
    {
        realm.close();
    }

    public interface OnSetWalletDefaultListener {
        void onSetDefault(Wallet wallet);
    }
}
