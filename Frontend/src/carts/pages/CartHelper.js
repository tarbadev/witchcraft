export const getCartTitle = (createdAt) => {
  if (Date.parse(createdAt)) {
    const date = new Date(createdAt)
    const dateString = `${date.getMonth()}/${date.getDate()}/${date.getFullYear()}`
    const timeString = `${date.getHours()}:${date.getMinutes()}`
    return `Cart created the ${dateString} at ${timeString}`
  } else {
    return ''
  }
}