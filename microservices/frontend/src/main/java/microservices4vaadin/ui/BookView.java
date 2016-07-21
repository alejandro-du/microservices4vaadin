package microservices4vaadin.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import microservices4vaadin.rest.controller.BookController;
import microservices4vaadin.rest.resource.Book;

@SpringView(name = BookView.VIEW_NAME)
public class BookView extends Panel implements View {

    private static final long serialVersionUID = -7001285092564194997L;

    public static final String VIEW_NAME = "Book";

    private boolean initialized = false;

    @Autowired
    private BookController bookController;

    private VerticalLayout layout;


    @PostConstruct
    void init() {

        layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        setSizeFull();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        if (!initialized) {
            layout.addStyleName("dashboard-view");

            layout.addComponent(buildHeader());
            layout.addComponent(buildBody());

            setContent(layout);
            Responsive.makeResponsive(layout);
            initialized = true;
        }
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        Responsive.makeResponsive(header);

        Label title = new Label("Books");
        title.setSizeFull();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        return header;
    }

    private Component buildBody() {
        VerticalLayout bodyLayout = new VerticalLayout();
        bodyLayout.setSizeFull();

        Book book = bookController.findOne(null);

        Label greetings = new Label(new Label("Book <b>" + book.getName()
        + "</b> found."));
        greetings.setContentMode(ContentMode.HTML);
        greetings.setWidth(null);

        bodyLayout.addComponent(greetings);
        bodyLayout.setComponentAlignment(greetings, Alignment.BOTTOM_CENTER);

        return bodyLayout;
    }
}
