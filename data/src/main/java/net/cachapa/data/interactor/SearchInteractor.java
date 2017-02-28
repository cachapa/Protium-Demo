package net.cachapa.data.interactor;

import net.cachapa.data.gateway.GiphyGateway;
import net.cachapa.data.model.Page;
import net.cachapa.protium.Interactor;
import net.cachapa.protium.ObservableModel;
import net.cachapa.protium.Task;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SearchInteractor extends Interactor {
    private GiphyGateway gateway;
    private String query;
    private Page page;
    private ObservableModel<Page> observable;
    private Future future;

    public SearchInteractor(ExecutorService service, GiphyGateway gateway, String query) {
        super(service);
        this.gateway = gateway;
        this.query = query;

        page = new Page();
        observable = new ObservableModel<>(page);
    }

    public ObservableModel<Page> getObservable() {
        return observable;
    }

    public void fetch() {
        if (future == null || future.isDone()) {
            future = execute(new SearchTask(), observable);
        }
    }

    /* Tasks */
    private class SearchTask implements Task<Page> {
        @Override
        public Page run() throws IOException {
            Page nextPage = gateway.search(query, page.size());
            page.stitch(nextPage);
            return page;
        }
    }
}
