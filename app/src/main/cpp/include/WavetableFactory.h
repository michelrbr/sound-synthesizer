#pragma once
#include <vector>

namespace soundsynthesizer {
    enum class Wavetable;

    class WavetableFactory {
    public:
        std::vector<float> getWaveTable(Wavetable wavetable);

    private:
        std::vector<float> sineWaveTable();
        std::vector<float> triangleWaveTable();
        std::vector<float> squareWaveTable();
        std::vector<float> sawWaveTable();

        std::vector<float> _sineWaveTable;
        std::vector<float> _triangleWaveTable;
        std::vector<float> _squareWaveTable;
        std::vector<float> _sawWaveTable;
    };
}
