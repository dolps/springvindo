package com.dolp.vindo.domain;

public enum WindDirection {
    N(0, "N"),
    NNE(23, "NNØ"),
    NE(45, "NØ"),
    ENE(68, "ENØ"),
    E(90, "Ø"),
    ESE(113, "ØSØ"),
    SE(135, "SØ"),
    SSE(153, "SSØ"),
    S(180, "S"),
    SSW(203, "SSV"),
    SW(225, "SV"),
    WSW(248, "VSV"),
    W(270, "V"),
    WNW(293, "VNV"),
    NW(315, "NV"),
    NNW(338, "NNV");

    private String norwegianName;
    private int degree;

    private WindDirection(int degree, String norwegianName) {
        this.norwegianName = norwegianName;
        this.degree = degree;
    }

    public static int getWindDirectionFromString(String text) {
        WindDirection result = null;
        for (WindDirection windDirection : WindDirection.class.getEnumConstants()) {
            if (windDirection.norwegianName.equals(text)) {
                result = windDirection;
                break;
            }
        }
        if (result != null) {
            return result.degree;
        }

        return -1;
    }

    public static String getWindDirectionFromValue(int value) {
        if (value >= 0 && value < 23) {
            return WindDirection.N.norwegianName;
        } else if (value >= 23 && value < 45) {
            return WindDirection.NNE.norwegianName;
        } else if (value >= 45 && value < 68) {
            return WindDirection.NE.norwegianName;
        } else if (value >= 68 && value < 90) {
            return WindDirection.ENE.norwegianName;
        } else if (value >= 90 && value < 113) {
            return WindDirection.E.norwegianName;
        } else if (value >= 113 && value < 135) {
            return WindDirection.ESE.norwegianName;
        } else if (value >= 135 && value < 153) {
            return WindDirection.SE.norwegianName;
        } else if (value >= 153 && value < 180) {
            return WindDirection.SSE.norwegianName;
        } else if (value >= 180 && value < 203) {
            return WindDirection.S.norwegianName;
        } else if (value >= 203 && value < 225) {
            return WindDirection.SSW.norwegianName;
        } else if (value >= 225 && value < 248) {
            return WindDirection.SW.norwegianName;
        } else if (value >= 248 && value < 270) {
            return WindDirection.WSW.norwegianName;
        } else if (value >= 270 && value < 293) {
            return WindDirection.W.norwegianName;
        } else if (value >= 293 && value < 315) {
            return WindDirection.WNW.norwegianName;
        } else if (value >= 315 && value < 338) {
            return WindDirection.NW.norwegianName;
        }

        return WindDirection.NNW.norwegianName;
    }
}
