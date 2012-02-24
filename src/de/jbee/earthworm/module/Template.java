package de.jbee.earthworm.module;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import de.jbee.data.Dataset.MemberProperty;
import de.jbee.data.Dataset.ValueProperty;

public class Template<T>
		implements Component<T> {

	private final List<TemplateAttr<T, ?>> attrs = new LinkedList<TemplateAttr<T, ?>>();
	private final List<TemplateTag<T>> tags = new LinkedList<TemplateTag<T>>();

	// name als Teil von Tag oder hier in die methode aufnehmen ?

	// eigentlich will man hier noch typen damit attribute nur formal korrekte werte bekommen können
	// das wären String / enum / int - man muss von allen auf string kommen

	public static class DataBinder<T>
			extends ComponentBinder<T> {

		private final Template<T> template;
		private final Tag tag;

		DataBinder( Template<T> template, Tag tag ) {
			super();
			this.template = template;
			this.tag = tag;
		}

		// man erkennt ja am markup, ob es nur ein leers platzhalter-tag ist (ersetzen) oder es inhalt gibt (davon ausgehen, dass die component das berücksichtigt
		public <V> ComponentBinder<V> using( MemberProperty<? super T, V> path ) {

			return null;
		}

		public <V> ListComponentBinder<V> listing( MemberProperty<? super T, V> path ) {
			return null;
		}

		// TODO das kann man sogar noch als builder weiterführen - sodass man beliebige folge "uses" bedingt zeigen kann 
		public ComponentBinder<T> dependsOn( ValueProperty<? super T, Boolean> path ) {
			return dependsOn( Fulfills.trueValue( path ) );
		}

		public ComponentBinder<T> dependsOn( Conditional<? super T> condition ) {

			return null;
		}

		private void bindTag( Tag tag, Component<? super T> component ) {
			template.tags.add( new TemplateTag<T>( tag, component ) );
		}
	}

	public static class ComponentBinder<T> {

		public void is( Component<? super T> component ) {

		}

		public void is( Class<? extends Component<? super T>> type ) {

		}
	}

	public static class ListComponentBinder<T>
			extends ComponentBinder<T> {

		public void is( Component<? super T>... components ) {

		}

		public void is( Class<? extends Component<? super T>>... components ) {

		}
	}

	public DataBinder<T> the( Tag tag ) {
		return null;
	}

	public <V extends CharSequence> void bind( Attr<V> attr, ValueProperty<? super T, V> path ) {

	}

	@Override
	public void prepare( PreparationCycle<? extends T> cycle ) {
		// load markup
		String markup = "a fix example";
		// find and sort used/bound tags and split markup appropriate -> create simple statically elements
		SortedMap<Integer, Component<T>> positions = new TreeMap<Integer, Component<T>>();
		for ( TemplateAttr<T, ?> b : attrs ) {
			int pos = markup.indexOf( b.attr.name() );
			positions.put( pos, b );
		}
		// insert:
		// - and unify components
		// - values
		// - renderable content
		int lastPos = 0;
		for ( Entry<Integer, Component<T>> e : positions.entrySet() ) {
			String before = markup.substring( lastPos, e.getKey() );
			cycle.constant( before );
			Component<T> comp = e.getValue();
			if ( comp instanceof TemplateComponent<?> ) {
				String tagMarkup = "compute it here";
				( (TemplateComponent<T>) comp ).prepareSubstitutional( tagMarkup, cycle );
			} else {
				comp.prepare( cycle );
			}
		}
	}

	// IBindable interface um sicherzustellen, dass Components auch für templates gedacht sind

	static class TemplateAttr<T, V extends CharSequence>
			implements Component<T> {

		final Attr<V> attr;
		final ValueProperty<T, V> path;

		TemplateAttr( Attr<V> tag, ValueProperty<T, V> path ) {
			super();
			this.attr = tag;
			this.path = path;
		}

		@Override
		public void prepare( PreparationCycle<? extends T> cycle ) {
			cycle.variable( path );
		}

	}

	static class TemplateTag<T>
			implements Component<T> {

		final Tag tag;
		final Component<? super T> component;

		TemplateTag( Tag tag, Component<? super T> component ) {
			super();
			this.tag = tag;
			this.component = component;
		}

		@Override
		public void prepare( PreparationCycle<? extends T> cycle ) {
			component.prepare( cycle );
		}
	}
}
