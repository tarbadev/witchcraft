export const getCartTitle = (createdAt) => {
  return `Cart created the ${getShortCartTitle(createdAt)}`
}

export const getShortCartTitle = createdAt => {
  if (Date.parse(createdAt)) {
    const date = new Date(createdAt)
    const dateString = `${date.getMonth()}/${date.getDate()}/${date.getFullYear()}`
    const timeString = `${date.getHours()}:${date.getMinutes()}`
    return `${dateString} at ${timeString}`
  } else {
    return ''
  }
}