import { VMSG } from './validationMessages'
import { isHalfWidthNumeric } from './validators'

// REC101 Purchase History Search

export function validateRec101(filters) {
  const errors = {}

  // Member No 
  if (filters.memberNo !== '' && filters.memberNo != null) {
    if (!isHalfWidthNumeric(filters.memberNo)) {
      errors.memberNo = VMSG.halfWidthNumeric('Member No.')
    }
  }

  // Total Amount Lower 
  if (filters.totalLower !== '' && filters.totalLower != null) {
    if (!isHalfWidthNumeric(filters.totalLower)) {
      errors.totalLower = VMSG.halfWidthNumeric('Total Amount (Lower Limit)')
    }
  }

  // Total Amount Uppe
  if (filters.totalUpper !== '' && filters.totalUpper != null) {
    if (!isHalfWidthNumeric(filters.totalUpper)) {
      errors.totalUpper = VMSG.halfWidthNumeric('Total Amount (Upper Limit)')
    }
  }

  // Total Amount consistency
  if (!errors.totalLower && !errors.totalUpper &&
      filters.totalLower !== '' && filters.totalUpper !== '' &&
      filters.totalLower != null && filters.totalUpper != null) {
    if (Number(filters.totalUpper) < Number(filters.totalLower)) {
      errors.totalUpper = VMSG.upperLower('Total Amount (Upper Limit)', 'Total Amount (Lower Limit)')
    }
  }

  // DAte range order
  const startComplete = filters.startYear && filters.startMonth && filters.startDay
  const endComplete = filters.endYear && filters.endMonth && filters.endDay
  if (startComplete && endComplete) {
    const start = buildDate(filters.startYear, filters.startMonth, filters.startDay)
    const end = buildDate(filters.endYear, filters.endMonth, filters.endDay)
    if (end < start) {
      errors.dateRange = VMSG.dateOrder('Order Date (Start)', 'Order Date (End)')
    }
  }

  return errors
}

function buildDate(y, m, d) {
  const pad = (n) => String(n).padStart(2, '0')
  return `${y}-${pad(m)}-${pad(d)}`   // ISO string, safe for lexical comparison
}