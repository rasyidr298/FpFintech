package id.fp.core.utils

import id.fp.core.domain.model.Sample

interface OnItemClicked {
    fun onEventClick(data: Sample) {}
}