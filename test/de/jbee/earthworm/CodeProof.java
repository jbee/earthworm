package de.jbee.earthworm;

import de.jbee.data.Dataset.RecordProperty;
import de.jbee.data.Dataset.ValueProperty;
import de.jbee.earthworm.module.Attribute;
import de.jbee.earthworm.module.BaseComponent;
import de.jbee.earthworm.module.BaseContainer;
import de.jbee.earthworm.module.Component;
import de.jbee.earthworm.module.Label;
import de.jbee.earthworm.module.PreparationCycle;
import de.jbee.earthworm.module.Tag;
import de.jbee.earthworm.module.Template;

public class CodeProof {

	static interface Album {

		ValueProperty<Album, String> title = null;
		ValueProperty<Album, Boolean> ep = null;
		RecordProperty<Album, Track> tracks = null;
		RecordProperty<Album, Track> hiddenTrack = null;
	}

	static interface CdBox
			extends Album {

		ValueProperty<CdBox, String> subtitle = null;
	}

	static interface Track {

		ValueProperty<Track, Integer> number = null;
		ValueProperty<Track, String> title = null;
		ValueProperty<Track, Long> length = null;
		RecordProperty<Track, Artist> artists = null;
	}

	static interface Artist {

		ValueProperty<Artist, String> name = null;
	}

	static enum MyTemplateTags
			implements Tag {
		hiddenTrack,
		foo,
		tracks,
		ep
	}

	static class MyPage
			implements Component<CdBox> {

		@Override
		public void prepare( PreparationCycle<? extends CdBox> cycle ) {
			Template<Album> t = new Template<Album>();
			t.bind( Attribute.name, Album.title );
			t.the( MyTemplateTags.hiddenTrack ).using( Album.hiddenTrack ).is( new TrackRow() );
			t.the( MyTemplateTags.foo ).is( BaseComponent.markup( "get out" ) );
			t.the( MyTemplateTags.ep ).dependsOn( Album.ep ).is(
					BaseComponent.markup( "This is a EP Album" ) );
			t.the( MyTemplateTags.tracks ).listing( Album.tracks ).is( TrackRow.class,
					TrackRow.class );
			t.prepare( cycle );
			Label<Album> test = new Label<Album>( Album.title );
			test.prepare( cycle );
			Label<CdBox> test2 = new Label<CdBox>( CdBox.subtitle );
			test2.prepare( cycle );
		}
	}

	static class TrackRow
			implements Component<Track> {

		@Override
		public void prepare( PreparationCycle<? extends Track> cycle ) {
			cycle.variable( Track.title );
			PreparationCycle<Artist> artists = cycle.in( BaseContainer.repeats( Track.artists ) );
			artists.in( BaseContainer.<Artist> recoversByStrippingOut() ).variable( Artist.name );
		}
	}

}
