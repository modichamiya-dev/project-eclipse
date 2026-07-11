package dev.modichamiya.eclipse.animation;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public final class TimelineInstance {
    public enum State { PLAYING, PAUSED, COMPLETED, CANCELLED }
    private final Timeline timeline; private final TimelineContext context; private final TimelineSink sink; private final AtomicReference<State> state=new AtomicReference<>(State.PLAYING); private long tick=-1; private double speed=1;
    TimelineInstance(Timeline timeline,TimelineContext context,TimelineSink sink){this.timeline=Objects.requireNonNull(timeline);this.context=Objects.requireNonNull(context);this.sink=Objects.requireNonNull(sink);}
    public State state(){return state.get();} public long tick(){return tick;} public void pause(){state.compareAndSet(State.PLAYING,State.PAUSED);} public void resume(){state.compareAndSet(State.PAUSED,State.PLAYING);} public void speed(double speed){if(!Double.isFinite(speed)||speed<=0)throw new IllegalArgumentException("speed must be positive");this.speed=speed;}
    public void cancel(){if(state.getAndSet(State.CANCELLED)!=State.CANCELLED)cleanup();}
    boolean advance(){if(state()!=State.PLAYING)return state()==State.PAUSED; long previous=tick; tick=Math.min(timeline.durationTicks(),tick+Math.max(1,Math.round(speed))); for(var track:timeline.tracks())track.apply(previous,tick,0,context,sink); if(tick>=timeline.durationTicks()){if(timeline.loop())tick=-1;else{state.set(State.COMPLETED);cleanup();return false;}} return true;}
    private void cleanup(){for(var track:timeline.tracks())track.cleanup(context,sink);}
}
