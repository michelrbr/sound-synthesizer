#pragma once

#include <memory>
#include <mutex>
#include "Wavetable.h"

namespace soundsynthesizer {

    class WavetableSynthesizer {
    public:
        void play();

        void stop();

        bool isPlaying() const;

        void setFrequency(float frequencyInHz);

        void setVolume(float volumeInDb);

        void setWavetable(Wavetable wavetable);

    private:
        bool _isPlaying = false;
    };
}
