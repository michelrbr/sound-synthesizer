#include "Log.h"
#include "Wavetable.h"
#include "WavetableSynthesizer.h"

namespace soundsynthesizer {
    void WavetableSynthesizer::play() {
        LOGD("synthesizer playing");
        _isPlaying = true;
    }

    void WavetableSynthesizer::stop() {
        LOGD("synthesizer stopped");
        _isPlaying = false;
    }

    bool WavetableSynthesizer::isPlaying() const {
        LOGD("synthesizer is playing method");
        return _isPlaying;
    }

    void WavetableSynthesizer::setFrequency(float frequencyInHz) {
        LOGD("synthesizer frequency: %.2f Hz", frequencyInHz);
    }

    void WavetableSynthesizer::setVolume(float volumeInDb) {
        LOGD("synthesizer volume: %.2f db", volumeInDb);
    }

    void WavetableSynthesizer::setWavetable(Wavetable wavetable) {
        LOGD("synthesizer wavetable selected: %.d", static_cast<int>(wavetable));
    }
}
