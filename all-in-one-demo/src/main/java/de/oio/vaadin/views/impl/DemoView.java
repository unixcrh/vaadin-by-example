package de.oio.vaadin.views.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import com.google.common.base.Preconditions;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.views.CustomLayoutView;

@Configurable
public class DemoView extends CustomLayoutView {

	private AbstractDemo demo;
	@Autowired
	private IMessageProvider messageProvider;

	public DemoView(ITemplatingService templatingService,
			SessionContext context, AbstractDemo demo) {
		super(templatingService, context, "demo");
		Preconditions.checkNotNull(demo);
		this.demo = demo;
	}

	@Override
	public void buildLayout() {
		super.buildLayout();

		DemoInfoPanel demoInfoPanel = new DemoInfoPanel(getTemplatingService(),
				getContext());
		demoInfoPanel.buildLayout();
		getLayout()
				.addComponent(demoInfoPanel.getContent(), "descriptionPanel");
		getLayout().addComponent(demo.getView(), "mainPanel");
	}

	private class DemoInfoPanel extends CustomLayoutView {
		public DemoInfoPanel(ITemplatingService templatingService,
				SessionContext context) {
			super(templatingService, context, "demoInfo");
		}

		@Override
		public void buildLayout() {
			super.buildLayout();
			if (DemoUI.isDebugMode()) {
				new ComponentHighlighterExtension(getLayout());
			}

			getLayout().addComponent(
					new Label(messageProvider.getMessage(demo.getDemoInfo()
							.getDemoHeadlineKey())), "demoHeadline");
			getLayout().addComponent(
					new Link(demo.getDemoInfo().getBlogPostTitle(),
							new ExternalResource(demo.getDemoInfo()
									.getBlogPostURI())), "linkToBlogPost");
			getLayout().addComponent(
					new Link(demo.getDemoInfo().getCodeHostingURI(),
							new ExternalResource(demo.getDemoInfo()
									.getCodeHostingURI())), "linkToDemoCode");
			getLayout().addComponent(
					new Label(messageProvider.getMessage(demo.getDemoInfo()
							.getShortDescriptionKey())), "shortDescription");
		}

	}

}
