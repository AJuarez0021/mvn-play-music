package com.work.music;

import java.util.concurrent.TimeUnit;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author linux
 */
public class Music implements AutoCloseable {

    private final Synthesizer midiSynth;

    public Music() throws MidiUnavailableException {
        this.midiSynth = MidiSystem.getSynthesizer();
        this.midiSynth.open();
    }

    @Override
    public void close() throws Exception {
        if (midiSynth != null) {
            midiSynth.close();
        }
    }

    public void sound(int noteNumber, int duration) throws MidiUnavailableException {
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = midiSynth.getChannels();
        midiSynth.loadInstrument(instr[0]);
        mChannels[0].noteOn(noteNumber, 100);
        delay(duration);
        mChannels[0].noteOff(noteNumber, 0);
    }

    private void delay(int duration) {
        try {
            Thread.sleep(duration);
            Thread.sleep(TimeUnit.MICROSECONDS.toMillis(midiSynth.getLatency()));
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
