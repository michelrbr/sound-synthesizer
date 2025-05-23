#pragma once

#include <memory>
#include <mutex>
#include "Wavetable.h"
#include "WavetableFactory.h"
#include <oboe/Oboe.h>

namespace soundsynthesizer {

    class WavetableOscillator;

    class AudioPlayer;

    constexpr auto samplingRate = 48000;

    class WavetableSynthesizer {
    public:
        WavetableSynthesizer();

        ~WavetableSynthesizer();

        void play();

        void stop();

        bool isPlaying() const;

        void setFrequency(float frequencyInHz);

        void setVolume(float volumeInDb);

        void setWavetable(Wavetable wavetable);

    private:
        std::atomic<bool> _isPlaying = false;
        std::mutex _mutex;
        WavetableFactory _wavetableFactory;
        Wavetable _currentWavetable { Wavetable::SINE };
        std::shared_ptr<WavetableOscillator> _oscillator;
        std::unique_ptr<AudioPlayer> _audioPlayer;
    };
}
