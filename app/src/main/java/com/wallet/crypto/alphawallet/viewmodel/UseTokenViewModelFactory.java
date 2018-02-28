package com.wallet.crypto.alphawallet.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.wallet.crypto.alphawallet.interact.FetchTokensInteract;
import com.wallet.crypto.alphawallet.interact.FindDefaultNetworkInteract;
import com.wallet.crypto.alphawallet.interact.FindDefaultWalletInteract;
import com.wallet.crypto.alphawallet.interact.SignatureGenerateInteract;
import com.wallet.crypto.alphawallet.router.SalesOrderRouter;
import com.wallet.crypto.alphawallet.router.MyTokensRouter;
import com.wallet.crypto.alphawallet.router.SellTicketRouter;
import com.wallet.crypto.alphawallet.router.SignatureDisplayRouter;
import com.wallet.crypto.alphawallet.router.TicketTransferRouter;

/**
 * Created by James on 22/01/2018.
 */

public class UseTokenViewModelFactory implements ViewModelProvider.Factory {

    private final FetchTokensInteract fetchTokensInteract;
    private final FindDefaultWalletInteract findDefaultWalletInteract;
    private final MyTokensRouter myTokensRouter;
    private final TicketTransferRouter ticketTransferRouter;
    private final SignatureDisplayRouter signatureDisplayRouter;
    private final FindDefaultNetworkInteract findDefaultNetworkInteract;
    private final SignatureGenerateInteract signatureGenerateInteract;
    private final SalesOrderRouter salesOrderRouter;
    private final SellTicketRouter sellTicketRouter;

    public UseTokenViewModelFactory(
            FetchTokensInteract fetchTokensInteract,
            FindDefaultWalletInteract findDefaultWalletInteract,
            SignatureGenerateInteract signatureGenerateInteract,
            MyTokensRouter myTokensRouter,
            TicketTransferRouter ticketTransferRouter,
            SignatureDisplayRouter signatureDisplayRouter,
            FindDefaultNetworkInteract findDefaultNetworkInteract,
            SalesOrderRouter salesOrderRouter,
            SellTicketRouter sellTicketRouter) {
        this.fetchTokensInteract = fetchTokensInteract;
        this.findDefaultWalletInteract = findDefaultWalletInteract;
        this.myTokensRouter = myTokensRouter;
        this.findDefaultNetworkInteract = findDefaultNetworkInteract;
        this.signatureDisplayRouter = signatureDisplayRouter;
        this.signatureGenerateInteract = signatureGenerateInteract;
        this.ticketTransferRouter = ticketTransferRouter;
        this.salesOrderRouter = salesOrderRouter;
        this.sellTicketRouter = sellTicketRouter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UseTokenViewModel(fetchTokensInteract, findDefaultWalletInteract, signatureGenerateInteract, myTokensRouter, ticketTransferRouter, signatureDisplayRouter, findDefaultNetworkInteract, salesOrderRouter, sellTicketRouter);
    }
}